package com.example.testthis;

        import android.Manifest;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.Bundle;
        import android.speech.RecognitionListener;
        import android.speech.RecognizerIntent;
        import android.speech.SpeechRecognizer;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.CompoundButton;
        import android.widget.DatePicker;
        import android.widget.ImageButton;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;
        import android.widget.ToggleButton;

        import androidx.annotation.NonNull;
        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;


        import java.io.BufferedReader;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.text.SimpleDateFormat;
        import java.time.LocalDateTime;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Objects;

        import jp.wasabeef.richeditor.RichEditor;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    /** For Toolbar */
    private ImageButton toolbarLeftButton, toolbarCalButton;
    TextView toolbarTitle;

    /** For Editors */
    private RichEditor mEditor;
    private TextView mPreview;
    String check;
    private boolean changed = false;

    /** For Date and Time*/
    private String y, d, nTitle = "", m, h, min;
    SimpleDateFormat titleFormat = new SimpleDateFormat("d MMM h:m a");
    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM");


//    /** Form journalVariables */
//
//    String title, description, year, month, day, hour, minute, imageLink="", fileLink="";
//    Journal journal = new Journal();
//    Intent intent;
//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        intent = getIntent();
        setUI();
        addFuctionalityInEditor();


        //mPreview = (TextView) findViewById(R.id.preview);
       /* mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });*/
//        if( !check.equals("new_journal_from_journal_activity")) {
//            readFromFile();
//        }
    }

    private void addFuctionalityInEditor() {
        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                findViewById(R.id.action_undo).setBackground(getResources().getDrawable(R.color.grey600));
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override public void onClick(View v) {
                mEditor.setBold();

            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

//        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                mEditor.setHeading(4);
//            }
//        });

//        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                mEditor.setHeading(5);
//            }
//        });

//        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                mEditor.setHeading(6);
//            }
//        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.WHITE : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
//                        "dachshund");
                mEditor.insertImage("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRTBGGiVygHO3RkYHdz_k2GG0cOGBTy7qHlms_NmwxXflmdI0O3",
                        "dachshund");
//

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivity(intent);

            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }


    void setUI(){
        findXmlElements();
        setListeners();
//        initializeJournal();
        initializeUI();
    }

    public void findXmlElements(){

        mEditor = (RichEditor) findViewById(R.id.editor);

        toolbarCalButton = findViewById(R.id.journal_toolbar_calender);
        toolbarLeftButton = findViewById(R.id.journal_toolbar_back_arrow);
        toolbarTitle = findViewById(R.id.journal_toolbar_tv);

    }


    public void setListeners(){

        toolbarLeftButton.setOnClickListener(this);
        toolbarCalButton.setOnClickListener(this);




        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                if(changed == true) {
                    toolbarLeftButton.setImageResource(R.drawable.icon_check);
                }
                else{
                    changed = true;
                }
            }
        });



    }

    public void initializeUI() {
        mEditor.setEditorFontSize(16);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");

//        if(check.equals("journal_from_show_activity") || check.equals("old_journal_from_journal_activity")){
//            toolbarCalButton.setClickable(false);
//        }
    }


//    public void initializeJournal(){
//
//        check = intent.getStringExtra("status");
//        if(check.equals("new_journal_from_journal_activity")){
//
//            /**
//             * As this a new journal so we use the current time and date to
//             * the title set there attributes
//             *
//             */
//            Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            year = Integer.toString(calendar.get(Calendar.YEAR));
//            month = Integer.toString(calendar.get(Calendar.MONTH));
//            day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
//            hour = Integer.toString(calendar.get(Calendar.HOUR));
//            minute = Integer.toString(calendar.get(Calendar.MINUTE));
//            title = titleFormat.format(date);
//            toolbarTitle.setText(title);
//            journal.setTitle(title);
//            journal.setFileLink(title);
//            journal.setYear(year);
//            journal.setMonth(month);
//            journal.setDay(day);
//            journal.setHour(hour);
//            journal.setMinute(minute);
//            // showToast("New !!!");
//
//        }
//
//        else if(check.equals( "journal_from_show_activity")){
//
//            title = intent.getStringExtra("sa_edit_title");
//            description = intent.getStringExtra("sa_edit_description");
//            year = intent.getStringExtra("sa_edit_year");
//            month = intent.getStringExtra("sa_edit_month");
//            day  = intent.getStringExtra("sa_edit_day");
//            hour = intent.getStringExtra("sa_edit_hour");
//            minute = intent.getStringExtra("sa_edit_minute");
//            imageLink = intent.getStringExtra("sa_edit_imageLink");
//            fileLink = intent.getStringExtra("sa_edit_fileLink");
//
//            journal.setTitle(title);
//            journal.setFileLink(title);
//            journal.setYear(year);
//            journal.setMonth(month);
//            journal.setDay(day);
//            journal.setHour(hour);
//            journal.setMinute(minute);
//            journal.setDescription(description);
//            journal.setImageLink(imageLink);
//            journal.setFileLink(fileLink);
//        }
//
//        else if(check.equals("old_journal_from_journal_activity")){
//
//            title = intent.getStringExtra("ja_edit_title");
//            description = intent.getStringExtra("ja_edit_description");
//            year = intent.getStringExtra("ja_edit_year");
//            month = intent.getStringExtra("ja_edit_month");
//            day  = intent.getStringExtra("ja_edit_day");
//            hour = intent.getStringExtra("ja_edit_hour");
//            minute = intent.getStringExtra("ja_edit_minute");
//            imageLink = intent.getStringExtra("ja_edit_imageLink");
//            fileLink = intent.getStringExtra("ja_edit_fileLink");
//
//            journal.setTitle(title);
//            journal.setFileLink(title);
//            journal.setYear(year);
//            journal.setMonth(month);
//            journal.setDay(day);
//            journal.setHour(hour);
//            journal.setMinute(minute);
//            journal.setDescription(description);
//            journal.setImageLink(imageLink);
//            journal.setFileLink(fileLink);
//        }
//
//        else{
//            showToast("Faltu");
//        }
//    }
//
//    public void writeToFile(String text){
//
//        try {
//            FileOutputStream fileOutputStream = openFileOutput(journal.getFileLink(), Context.MODE_PRIVATE);
//            fileOutputStream.write(text.getBytes());
//            fileOutputStream.close();
//            Toast.makeText(getApplicationContext(), "data is saved", Toast.LENGTH_SHORT).show();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void readFromFile(){
//        try {
//            FileInputStream fileInputStream = openFileInput(journal.getFileLink());
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line;
//            StringBuffer stringBuffer = new StringBuffer();
//
//            while((line = bufferedReader.readLine()) != null){
//                stringBuffer.append(line+"\n");
//            }
//            mEditor.setHtml(String.valueOf(stringBuffer));
//            showToast(stringBuffer.toString());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    public void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onClick(View view) {
//        if(view == toolbarCalButton){
//
//            DatePickerFragment fragment = new DatePickerFragment();
//            fragment.show(getSupportFragmentManager(), "tag1");
//
//
//
//        } else if(view == toolbarLeftButton){
//
//            journal.setFileLink(journal.getTitle());
//            if(mEditor.getHtml().length() >= 30){
//                journal.setDescription(mEditor.getHtml().substring(0, 30));
//            }
//            else{
//                journal.setDescription(mEditor.getHtml());
//            }
//
//            writeToFile(mEditor.getHtml());
//            /**
//             * Lot to do :(
//             */
//            SQLiteDatabaseHelper sqLiteDatabaseHelper = new SQLiteDatabaseHelper(this);
//            SQLiteDatabase sqLiteDatabase = sqLiteDatabaseHelper.getWritableDatabase();
//
//            sqLiteDatabaseHelper.insertJournal(journal);
//            onBackPressed();
//        }
    }


    /**
     *For Date and Time picker fragment
     */

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
//
//        /** Formatting Selected Date so that we can set the date on EditText */
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, y);
//        calendar.set(Calendar.MONTH, m);
//        calendar.set(Calendar.DAY_OF_MONTH, d);
//        String selectedDate = dateFormat.format(calendar.getTime());
//
//        /** Setting Selected Date to the EditText */
//        nTitle += selectedDate;
//
//        /** Assigning Selected Date to the following variables
//         * so that we can use these variables to create task object */
//        year = Integer.toString(calendar.get(Calendar.YEAR));
//        month = Integer.toString(calendar.get(Calendar.MONTH));
//        day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
//
//        TimePickerFragment timePickerFragment = new TimePickerFragment();
//        timePickerFragment.show(getSupportFragmentManager(), "tag");

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int h ,int m) {
//
//        hour = Integer.toString(h);
//        minute = Integer.toString(m);
//
//        /** Formatting Selected Time so that we can set the time on EditText */
//        String amPm = " am";
//        if (h >= 12) {
//            amPm = " pm";
//            h = h - 12;
//        }
//        nTitle+=" ";
//        nTitle += String.format("%02d:%02d", h, m) + amPm;
//
//        title = nTitle;
//        toolbarTitle.setText(title);
//        journal.setTitle(title);
//        journal.setTitle(title);
//        journal.setFileLink(title);
//        journal.setYear(year);
//        journal.setMonth(month);
//        journal.setDay(day);
//        journal.setHour(hour);
//        journal.setMinute(minute);
//
//        nTitle = "";

    }



    @Override
    protected void onPause() {
        super.onPause();
    }

}
