package com.biz.myapp02;

import android.content.Intent;
import android.os.Bundle;

import com.biz.myapp02.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1=findViewById(R.id.txt1);
        txt1.setOnClickListener(this);

        /*
        context메뉴에 register만 해주면 onClick 메소드에 뭐 정의해줄 필요 없ㅎ이 길게 누름 이벤트때
        자동으로 발동
         */
        registerForContextMenu(txt1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        registerForContextMenu(fab);
        fab.setOnClickListener(this);
    }//onCreate end

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*
        option 메뉴중 터치된 메뉴의 id값 추출하기
         */
        int m_id=item.getItemId();
        if(m_id==R.id.m_login){
            /*
            Intent는 현재 Activity에서 다른 Activity로 전환하기 위한 클래스
            생성자에 현재의 Activity와 새로 열릴 클래스를 매개변수로 전달하라.
             */
            Intent loginIntent=new Intent(
                    /*
                    MainActivity의 주소(메모리 주소). 문맥교환 하라.
                     */
                    MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            return true;
        } else if(m_id==R.id.m_settings){
            Intent setIntent=new Intent(MainActivity.this,Main3Activity.class);
            startActivity(setIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    menu resource를 현재 activity의 Appbar에 표시하기 위해서 메뉴를 설정하는 method
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 확장하다. 리소스를 가져와서 코드에 부착하라.
        getMenuInflater().inflate(R.menu.menu_main,menu);

        //메뉴를 Activity에 부착할때 제일먼저 수정할 코드
        return true;
    }

    /*
    화면에 표시된  view에 contextMenu를 설정하기 위한 method
    설정된 view에 따라 반응형 contextMenu 적용이 가능.

    ContextMenu 설정
    1. menu.xml 리소스를 작성
    2. Activity에서 onCreateContextMenu() method를 저으이한후 menu.xml 리소스를 inflater하여 설정
    3. onCreate() method에서 contextMenu를 적용할 view에 registerforContext()설정하면 된다.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        int id=v.getId();
        if(id== R.id.txt1){
            getMenuInflater().inflate(R.menu.menu_hello_context,menu);
        }else if(id==R.id.fab){
            getMenuInflater().inflate(R.menu.menu_fab_context,menu);
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //ContextMenu.ContextMenuInfo m_info=item.getMenuInfo();
        int m_id=item.getItemId();
        if(m_id==R.id.m_fab_con_settings){
          Intent setIntent=new Intent(MainActivity.this,Main3Activity.class);
          startActivity(setIntent);
          return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int v_id=v.getId();
        String msg="hi";
        if(v_id==R.id.txt1){
            msg+="\n i'm hello button";
        } else if(v_id==R.id.fab){
            msg+="\n i'm faloting";
        }
        Snackbar.make(v,msg,Snackbar.LENGTH_SHORT).show();
    }
}
