package com.pongo.biibisoft.donkomi_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.View;


import androidx.appcompat.app.AppCompatDialogFragment;


import java.util.ArrayList;

public class MagicBoxes extends AppCompatDialogFragment {

  private final Context context;

  public MagicBoxes(Context context) {
    this.context = context;
  }

  public Dialog constructASimpleDialog(String title, String msg, final MagicBoxCallables magicInterface) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title)
        .setMessage(msg)
        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            magicInterface.negativeBtnCallable();
          }
        }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        magicInterface.positiveBtnCallable();
      }
    });
    return builder.create();
  }


  public Dialog constructCustomDialog(String title, View v, final MagicBoxCallables magicInterface) {
//    LayoutInflater inflater = getActivity().getLayoutInflater();
//    View view = inflater.inflate(R.layout.start_errand_custom_dialog,null);
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setView(v)
        .setTitle(title)
        .setPositiveButton("Start Now", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            magicInterface.positiveBtnCallable();
          }
        })
        .setNegativeButton("Add to list", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            magicInterface.negativeBtnCallable();
          }
        });

    return builder.create();

  }

  public Dialog constructCustomDialog(String title, View v, final MagicBoxCallables magicInterface, String positiveActionText, String negativeActionText) {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setView(v)
        .setTitle(title)
        .setPositiveButton(positiveActionText, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            magicInterface.positiveBtnCallable();
          }
        })
        .setNegativeButton(negativeActionText, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            magicInterface.negativeBtnCallable();
          }
        });

    return builder.create();

  }

//  public Dialog constructErrandErrorDialog(String title, String fatal, String semiError, String negative, String positive, final MagicBoxCallables magicInterface) {
//    LayoutInflater inflater = LayoutInflater.from(context);
//    View view = inflater.inflate(R.layout.errand_error_dialog_layout, null, false);
//    TextView fatalHeader, fatalErr, semiErrHeader, semiErr;
//    fatalHeader = view.findViewById(R.id.fatal_error_header);
//    fatalErr = view.findViewById(R.id.fatal_error);
//    semiErrHeader = view.findViewById(R.id.semi_error_header);
//    semiErr = view.findViewById(R.id.semi_error);
//    if (fatal.isEmpty()) {
//      fatalHeader.setVisibility(View.GONE);
//      fatalErr.setVisibility(View.GONE);
//    }
//    fatalErr.setText(fatal);
//    semiErr.setText(semiError);
//    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//    builder.setView(view)
//        .setTitle(title)
//        .setPositiveButton(positive, new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialogInterface, int i) {
//            magicInterface.positiveBtnCallable();
//          }
//        })
//        .setNegativeButton(negative, new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialogInterface, int i) {
//            magicInterface.negativeBtnCallable();
//          }
//        });
//
//    return builder.create();
//
//  }

  public Dialog constructSimpleOneActionDialog(String title, String msg, String actionTitle, final OneAction action) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(actionTitle, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            action.callback();
          }
        });

    return builder.create();

  }

  public Dialog constructCustomDialogOneAction(String title, View v, String actionTitle, final MagicBoxCallables magicInterface) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setView(v)
        .setTitle(title)
        .setPositiveButton(actionTitle, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            magicInterface.positiveBtnCallable();
          }
        });

    return builder.create();

  }
//
//  public Dialog constructRatingDialog(String contentHeadline, Bitmap bitmap, final RatingButtonActions buttonActions) {
//    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//    View v = LayoutInflater.from(context).inflate(R.layout.smiley_rating_dialog, null, false);
//    TextView headline = v.findViewById(R.id.headline_text);
//    headline.setText(contentHeadline);
//    final SmileyRating smileyRator = v.findViewById(R.id.smile_rating);
//    ImageView img = v.findViewById(R.id.rider_profile_img);
//    builder.setView(v).setPositiveButton("Done", new DialogInterface.OnClickListener() {
//      @Override
//      public void onClick(DialogInterface dialogInterface, int i) {
//        buttonActions.onFinish(smileyRator.getSelectedSmiley().getRating());
//      }
//    });
//    return builder.create();
//  }


//  ----------------------- END OF THE LINE FOR THIS CLASS -------------------------
}


interface TagDialogContentCallable {
  void getContent(ArrayList<String> tags);
}

//interface PhoneNumberDialogContentCallable {
//  void getContent(ArrayList<PaymentContact> phoneNumbers);
//}

interface RatingButtonActions {
  void onFinish(int rating);
}

interface OneAction {
  void callback();
}

interface MagicBoxCallables {
  void negativeBtnCallable();

  void positiveBtnCallable();
}