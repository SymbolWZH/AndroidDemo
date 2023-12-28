package com.origin.cameraalbumtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;


    private  static final int CHOOSE_PHOTO = 2;
    /*
    * onActivityResult 是 Android 中的一个回调方法，用于处理通过 startActivityForResult 启动的活动（Activity）返回的结果。
    * 在用户执行某个操作并在子活动（例如相机、相册）中选择或拍摄完照片后，系统会调用 onActivityResult 来传递相应的结果。*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK)
                {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                // 检查选择图片的操作是否成功
                if (resultCode == RESULT_OK) {
                    // 根据Android版本调用不同的图片处理方法
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 处理Android 4.4及以上版本的图片逻辑
                        handleImageOnKitKat(data);
                    } else {
                        // 处理Android 4.4以下版本的图片逻辑
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        picture = findViewById(R.id.picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage =new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.origin.cameraalbumtest.fileprovider",outputImage);
                }else{
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        /**
         *
         这段代码是一个点击事件监听器，用于处理从相册选择图片的逻辑。
         当用户点击某个按钮（假设为 chooseFromAlbum）时，会检查是否有写入外部存储的权限。
         如果没有权限，会请求权限；如果已经有权限，则调用 openAlbum() 方法打开相册。
         */
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检查是否有写入外部存储的权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    // 如果没有权限，请求权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    // 如果已经有权限，打开相册
                    openAlbum();
                }
            }
        });


    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
            break;
            default:
        }
    }

    /**
     * 这段代码是用于处理在Android 4.4及以上版本中选择图片的逻辑。主要功能是根据不同的URI类型获取图片的路径，然后显示该图片。
     * @param data
     */
    @SuppressLint("LongLogTag")
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

        // 检查是否为Document类型的URI
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 解析出文档ID，并根据ID构造查询条件
            String docId = DocumentsContract.getDocumentId(uri);
            // 如果是Media Provider类型的URI
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                // 获取图片路径
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }
            // 如果是Downloads Provider类型的URI
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                // 构造包含下载ID的ContentUri
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                // 获取图片路径
                imagePath = getImagePath(contentUri, null);

            }
        }
        // 如果是普通Content类型的URI
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 获取图片路径
            imagePath = getImagePath(uri, null);
        }
        // 如果是File类型的URI
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 直接获取图片路径
            imagePath = uri.getPath();
        }

        // 显示图片
        displayImage(imagePath);
    }

    /*
    这段代码用于显示图片。它接收一个图片路径 imagePath 作为参数，如果路径不为 null，
    则通过该路径解码出 Bitmap，并将其设置到一个 ImageView（假设为 picture）中。
    如果路径为 null，则显示一个短时提示消息，通知用户获取图片失败。*/
    private void displayImage(String imagePath) {
        // 如果图片路径不为null
        if (imagePath != null) {
            // 通过文件路径解码出Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            // 将Bitmap设置到ImageView中显示
            picture.setImageBitmap(bitmap);
        } else {
            // 如果图片路径为null，显示获取图片失败的提示消息
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /*这段代码是一个辅助方法，用于从给定的 URI 和查询条件获取图片路径。它使用 getContentResolver()
     获取内容解析器，然后通过查询指定的 URI 和选择条件，获取包含图片路径的 Cursor。*/
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;

        // 使用ContentResolver进行查询
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);

        if (cursor != null) {
            // 如果Cursor不为空且移动到第一行成功
            if (cursor.moveToFirst()) {
                // 获取图片路径
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }
    /*这段代码用于处理在Android 4.4以下版本中选择图片的逻辑。它接收一个 Intent 对象作为参数，
    从中获取图片的 URI，然后调用 getImagePath 方法获取图片路径，最后通过 displayImage 方法显示图片。*/
    private void handleImageBeforeKitKat(Intent data) {
        // 从Intent中获取图片的URI
        Uri uri = data.getData();

        // 通过URI获取图片路径
        String imagePath = getImagePath(uri, null);

        // 显示图片
        displayImage(imagePath);
    }

}