package com.example.limsebatchmanagement.Utility;

import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import java.util.concurrent.atomic.AtomicInteger;

public class PdfDraw {
    private static final int MAX_WIDTH = 1130, MAX_HEIGHT = 1730;
    public static PdfDocument.Page createPage (PdfDocument pdfDocument, int pageNumber){
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(MAX_WIDTH,MAX_HEIGHT,pageNumber).create();
        return pdfDocument.startPage(pageInfo);
    }
    public static void drawBitMap (Context context, Canvas canvas, int idImage, int width, int height, int layoutWidth, int layoutHeight){
        Bitmap bitMapImage = BitmapFactory.decodeResource(context.getResources(), idImage);
        Bitmap scaleBitMapImage = Bitmap.createScaledBitmap(bitMapImage, width, height, false);
        canvas.drawBitmap(scaleBitMapImage,layoutWidth,layoutHeight,new Paint());
    }
    public static void drawText (Context context, Canvas canvas, int textSize, int textColor, int style, String textToWrite, int layoutWidth, int layoutHeight){
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(context.getResources().getColor(textColor));
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,style));
        canvas.drawText(textToWrite,layoutWidth,layoutHeight,paint);
    }
    public static void drawRect (Context context, Canvas canvas, int color, int left, int top, int right, int bottom){
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(color));
        canvas.drawRect(left,top,right,bottom,paint);
    }
    public static void drawLine (Context context, Canvas canvas, int color,int startX,int stopX,int startY,int stopY){
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(color));
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }
    public static boolean checkChangePage(int heightToPrint,AtomicInteger y){
        if(y.get()+heightToPrint>1700)
            return true;
        return false;
    }
    public static PdfDocument.Page setNewPage(PdfDocument pdfDocument,PdfDocument.Page page, int pageNumber){
        pdfDocument.finishPage(page);
        pageNumber++;
        page = createPage(pdfDocument,pageNumber);
        return page;
    }
}
