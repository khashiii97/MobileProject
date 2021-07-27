package com.example.finalproject.Utils;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFUtils {

    public static String generateAndSaveFirstPageImage(Context context, Uri uri, int bookId) {
        try {
            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(uri, "r");
            int pageNum = 0;
            PdfiumCore pdfiumCore = new PdfiumCore(context);
            try {
                PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
                pdfiumCore.openPage(pdfDocument, 0); // open first page for cover.

                int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNum);
                int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNum);

                // do a fit center to 1920x1080
                //double scaleBy = Math.min(1080 / (double) width, //
                //    1920 / (double) height);
                //width = (int) (width * scaleBy);
                //height = (int) (height * scaleBy);

                // ARGB_8888 - best quality, high memory usage, higher possibility of OutOfMemoryError
                // RGB_565 - little worse quality, twice less memory usage
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNum, 0, 0, width, height);
                //if you need to render annotations and form fields, you can use
                //the same method above adding 'true' as last param

                String fileName = String.valueOf(bookId) + ".jpg";
                // save the file
                File outputFile = new File(context.getFilesDir(), fileName);
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                // a bit long running
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                outputStream.close();
                fd.close();
                pdfiumCore.closeDocument(pdfDocument); // important!
                Log.d("pdfffff", fileName);
                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String getRealPathFromURI(Uri contentURI,Context context) {
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            return cursor.getString(idx);
        }
    }
}
