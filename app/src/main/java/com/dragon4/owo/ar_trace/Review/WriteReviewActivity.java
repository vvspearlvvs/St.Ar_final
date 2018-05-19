package com.dragon4.owo.ar_trace;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon4.owo.ar_trace.Configure.ClientInstance;
import com.dragon4.owo.ar_trace.Model.User;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 예린 on 2017-05-25.
 */

public class WriteReviewActivity extends AppCompatActivity {

    String locationCode;

    private Uri mImageCaptureUri;   //이미지 Uri
    private String absolutePath;

    private  static final int PICK_FROM_CAMERA = 0;
    private  static final int PICK_FROM_ALBUM = 1;
    private  static final int CROP_FROM_IMAGE = 2;

    RatingBar ratingBar;    // 별점
    EditText contentET;
    TextView placeNameTV;
    TextView ratingTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write2);

        //Intent로 넘어온 locationCode 가지고있기
        locationCode = getIntent().getExtras().getString("locationCode");
        ratingBar = (RatingBar) findViewById(R.id.review_write_ratingbar);
        final TextView ratingText = (TextView) findViewById(R.id.review_write_rating_text);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                ratingText.setText("" + rating);
            }
        });

        placeNameTV = (TextView) findViewById(R.id.write_review_placeName);
        placeNameTV.setText(getIntent().getExtras().getString("placeName"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK) {
            return;
        }

        switch(requestCode) {
            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                Log.d("가져온 앨범사진: ", mImageCaptureUri.getPath().toString());
                // 이후의 처리가 카메라와 같아 break 없이 진행
            }
            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 후 리사이즈할 이미지 크기를 결정.
                // 이후에 이미지 크롭 어플리케이션 호출.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 500);    // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 500);    // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1);    // CROP한 이미지의 x축 크기
                intent.putExtra("aspectY", 1);    // CROP한 이미지의 y축 크기
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);    // CROP_FROM_IMAGE case문으로 이동
                break;
            }
            case CROP_FROM_IMAGE:
            {
                // CROP이 된 이후의 이미지를 넘겨받음.
                // 부가적인 작업(이미지뷰에 이미지 보여주기 등) 이후에 임시파일 삭제
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/St.AR/" + System.currentTimeMillis() + ".jpg";

                if(extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    // 이미지 뷰에 CROP된 BITMAP 보여주기
                    ImageView selectedImage = (ImageView) findViewById(R.id.write_review_addphoto);
                    selectedImage.setImageBitmap(photo);

                    // CROP된 이미지를 외부저장소 및 앨범에 저장
                    storeCropImage(photo, filePath);

                    absolutePath = filePath;
                    break;
                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()) {
                    f.delete();
                }
            }
        }
    }

    /* bitmap 저장 */
    private void storeCropImage(Bitmap bitmap, String filePath) {
        // "St.AR" 폴더를 생성하여 이미지 저장
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/St.AR";
        File directory_CROPIMAGE = new File(dirPath);

        // 디렉토리에 St.AR 폴더가 없을 때 (새로 이미지를 저장할 때)
        if(!directory_CROPIMAGE.exists()) {
            directory_CROPIMAGE.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // CROP된 사진을 앨범에 보이도록 갱신
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 리뷰 쓰기 - 사진 추가 */
    public void onAddPhotoBtnClicked(View view) {

        // "사진촬영"버튼 클릭시
        final DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /*
                // 임시로 사용할 파일의 경로
                String tempURL = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), tempURL));

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);*/

                if(intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;

                    String tempURL = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    photoFile = new File(Environment.getExternalStorageDirectory(), tempURL);
                    //mImageCaptureUri = photoFile.getAbsolutePath();

                    if(photoFile != null) {
                        //mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), tempURL));
                        mImageCaptureUri = Uri.fromFile(photoFile); // 임시 파일의 위치, 경로 가져오기
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri); // 임시 파일 위치에 저장
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    }
                }

            }
        };

        // "앨범선택"버튼 클릭시
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        };

        // "취소"버튼 클릭시
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                //.setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    /* "등록" 버튼 클릭시 */
    public void onReviewWriteRegistBtnClicked (View view) {
        float ratingScore = ratingBar.getRating();
        contentET = (EditText) findViewById(R.id.review_write_edittext);
        String content = contentET.getText().toString();

        if(absolutePath == null) {
            Toast.makeText(this, "리뷰 사진을 등록해주세요!", Toast.LENGTH_LONG).show();
        }else if(content.equals("")) {
            Toast.makeText(this, "리뷰 내용을 작성해주세요!", Toast.LENGTH_LONG).show();
        } else {
            String contents = "장소코드 : " + locationCode + " 이미지 : " + absolutePath + " 별점 : " + ratingScore + " 내용 : " + content;
            Log.d("리뷰등록: ", contents);

            // 이미지서버에 이미지 전송
            ClientInstance.getInstanceClient().uploadImageToServer(absolutePath);

            // 리뷰를 서버에 등록하기
            RegistReviewObj review = new RegistReviewObj(locationCode, placeNameTV.getText().toString(), content, User.getMyInstance().getUserEmail(), absolutePath, ratingScore, 0);
            if(ClientInstance.getInstanceClient().registReviewToServer(review)) {
                //리뷰 등록이 성공하면 reviewlist에서 그 아이템 삭제
                ClientInstance.getInstanceClient().deleteReviewListFromServer(User.getMyInstance().getUserEmail(), placeNameTV.getText().toString());

                Toast.makeText(this, "리뷰 등록 성공!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(WriteReviewActivity.this, WriteReviewSelectLocationActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "리뷰 등록 실패!!", Toast.LENGTH_LONG).show();
            }

        }

    }

}
