package com.ahnsafety.ex69cameratest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv);

    }

    public void clickPhoto(View view) {
        //Camera 앱을 실행
        Intent intent= new Intent();//묵시적 인텐트
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        //결과를 받아야 하므로 결과를 받을 수 있도록 카메라 액티비티 실행하지기
        startActivityForResult(intent, 100);

    }
    //startActivityForResult()로 실행한
    //액티비티가 종료된 후 자동으로 실행되는
    //콜백 메소드


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 100:

                //카메라앱의 실행결과 ok
                if(resultCode == RESULT_OK){
                    //캡쳐한 샂진 이미지를 이미지뷰에 보여주기

                    //결과물을 가져온 Intent객체(3번째 파라미터: data)에게 결과 받기

                    Uri uri=data.getData();//Uri : 리소스의 경로를 관리하는 객체(저장)

                    //디바이스마다 프로그램을 통해 실행한
                    //카메라 앱의 사진캡쳐방식이 다름.

                    //마시멜로우 부터 앱이 캡쳐한 이미지를
                    //파일로 저장하지 않고 Bitmap이미지로 데이터로
                    //결과를 돌려주는 디바이스들이 많음.

                    if(uri!=null){//파일로 저장되는 방식을 때
                        //카메라로 취득한 사진은 해상도가 너무 높아서
                        //이미지에 바로 사용 못하는 경우가 많음.
                        //이미지의 해상도를 리사이징 해서 사용함.
                        //이런 작업을 자동으로 해주는 라이브러리 사용(Glide, Picasso)
                        //iv.setImageURI(uri);
                        Toast.makeText(this, "uri로 사진 정보 획득", Toast.LENGTH_SHORT).show();
                        Glide.with(this).load(uri).into(iv);

                    }else{//Bitmap이미지 데티어만 줄때
                        Toast.makeText(this, "Bitmap 객체로 사진 정보 획득", Toast.LENGTH_SHORT).show();

                        //Bitmap개체를 Intent로 부터 Extra데이터로 전달 받음
                        Bundle bundle=data.getExtras();
                        Bitmap bm= (Bitmap) bundle.get("data");

                        Glide.with(this).load(bm).into(iv);
                    }

                }

                break;

        }
    }
}
