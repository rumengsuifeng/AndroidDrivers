package com.example.administrator.helloworld;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.administrator.hardware.*;

public class HelloWorldActivity extends AppCompatActivity {
    private Button button = null;       //定义按键
    private boolean ledon = false;      //相当于一个flag
    private CheckBox checkBoxled1 = null;   //定义复选框
    private CheckBox checkBoxled2 = null;
    private CheckBox Beep = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //调用父类onCreate方法
        setContentView(R.layout.activity_hello_world);      //给当前活动加载一个布局

        HardCor.ledOpen();      //静态方法调用，先是打开LED
        button = (Button) findViewById(R.id.button);        //从布局文件中拿到按键ID

        checkBoxled1 = (CheckBox) findViewById(R.id.LED1);  //从布局文件中拿到复选框的ID
        checkBoxled2 = (CheckBox) findViewById(R.id.LED2);
        Beep = (CheckBox) findViewById(R.id.BEEP);

        button.setOnClickListener(new View.OnClickListener() {      //设置按键的监听函数
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

//                HardCor hardCor = new HardCor();    //会调用到HardCor.java中的static块

                ledon = !ledon;
                if (ledon) {
                    button.setText("ALL ON");           //更改按键上的显示字符
                    checkBoxled1.setChecked(true);      //设置复选框选中
                    checkBoxled2.setChecked(true);
                    Beep.setChecked(true);

                    HardCor.ledCtrl(364, 1);
                    HardCor.ledCtrl(362, 1);
                    HardCor.ledCtrl(120, 1);
                } else {
                    button.setText("ALL OFF");          //更改按键上的显示字符
                    checkBoxled1.setChecked(false);     //设置复选框没选中
                    checkBoxled2.setChecked(false);
                    Beep.setChecked(false);
                    HardCor.ledCtrl(364, 0);
                    HardCor.ledCtrl(362, 0);
                    HardCor.ledCtrl(120, 0);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hello_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked  检查复选框的单击
        switch(view.getId()) {
            case R.id.LED1:
                if (checked)    //如果是选中
                {
                    Toast.makeText(HelloWorldActivity.this, "LED1 ON", Toast.LENGTH_SHORT).show();      //打印字符串到屏幕上
                HardCor.ledCtrl(364, 1);      //控制LED灯的亮灭
        }
            else{
            Toast.makeText(HelloWorldActivity.this, "LED1 OFF", Toast.LENGTH_SHORT).show();
            HardCor.ledCtrl(364, 0);
        }
                break;
            case R.id.LED2:             //LED的驱动标号计算公式是(x * 32) + 10, x是A的时候就是0，x是B的时候就是1，依次类推
                if (checked) {              //本例子用的是L，所以是11 * 32; 10就是后边跟的标号，加上10所以就是362
                    Toast.makeText(HelloWorldActivity.this, "LED2 ON", Toast.LENGTH_SHORT).show();
                    HardCor.ledCtrl(362, 1);
                }
            else{
                    Toast.makeText(HelloWorldActivity.this, "LED2 OFF", Toast.LENGTH_SHORT).show();
                    HardCor.ledCtrl(362, 0);
                }
                break;
            case R.id.BEEP:
                if (checked) {
                    Toast.makeText(HelloWorldActivity.this, "BEEP ON", Toast.LENGTH_SHORT).show();
                    HardCor.ledCtrl(120, 1);
                }
                else {
                    Toast.makeText(HelloWorldActivity.this, "BEEP OFF", Toast.LENGTH_SHORT).show();
                    HardCor.ledCtrl(120, 0);
                }
                break;
            // TODO: Veggie sandwich
        }
    }
}
