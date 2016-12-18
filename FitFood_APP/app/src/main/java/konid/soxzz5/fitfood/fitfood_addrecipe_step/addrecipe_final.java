package konid.soxzz5.fitfood.fitfood_addrecipe_step;

import android.app.ProgressDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import konid.soxzz5.fitfood.Manifest;
import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.utils.ScalingUtilities;
import konid.soxzz5.fitfood.utils.utils;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Soxzer on 15/12/2016.
 */

public class addrecipe_final extends Fragment implements View.OnClickListener {
    private com.mikepenz.iconics.view.IconicsImageView buttonChoose;
    private com.mikepenz.iconics.view.IconicsImageView buttonTake;
    private ImageView imageView;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String KEY_IMAGE = "image";
    private int REQUEST_IMAGE_CAPTURE = 2;
    private StorageReference storageReference;
    private Uri filePath;
    private String path;
    private Uri cameraPath;
    private String path_camera;
    private Uri outputFileUri;
    private File outputFile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_wizard_final, container, false);

        buttonChoose = (com.mikepenz.iconics.view.IconicsImageView) v.findViewById(R.id.wizard_bt_choose);
        buttonTake = (com.mikepenz.iconics.view.IconicsImageView) v.findViewById(R.id.wizard_bt_take);
        imageView = (ImageView) v.findViewById(R.id.wizard_iv_final);
        storageReference = FirebaseStorage.getInstance().getReference();
        buttonChoose.setOnClickListener(this);
        buttonTake.setOnClickListener(this);
        return v;
    }

    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.wizard_select_picture)), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path = filePath.toString();
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            try {
               Uri new_path = Uri.parse(decodeFile(path,1280,720));
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),new_path);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8),(int)(bitmap.getHeight()*0.8), true);
                bitmap = resized;
                imageView.setImageBitmap(resized);
                imageView.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeFile(outputFile.getAbsolutePath(), options);

                    try {
                        FileOutputStream outFile= new FileOutputStream(outputFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);
                        MediaScannerConnection.scanFile(getContext(), new String[] { path_camera }, new String[] { "image/jpeg" }, null);
                        outFile.flush();
                        outFile.close();
                    }catch (FileNotFoundException e) {
                        Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    } catch (IOException e) {
                        Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    filePath = Uri.parse("file://"+path_camera);
                    Picasso.with(getContext()).load(outputFile.getAbsolutePath()).centerCrop().resize(400,400).into(imageView);
            }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonChoose){
            showFileChooser();
        }
        if(v == buttonTake){
            dispatchTakePictureIntent();
        }
    }

    public void dispatchTakePictureIntent() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "Fitfood");
        if(!folder.exists())
        {
            folder.mkdirs();
        }
        path_camera = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "Fitfood" + File.separator + "FITFOOD_IMG_"+utils.shortUUID()+".jpg";
        outputFile = new File(path_camera);
        outputFileUri = Uri.fromFile(outputFile);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public Uri getFilePath() {
        return filePath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private String decodeFile(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/TMMFOLDER");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            String s = "tmp.png";

            File f = new File(mFolder.getAbsolutePath(), s);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }

}
