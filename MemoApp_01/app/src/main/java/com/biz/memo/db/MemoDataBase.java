package com.biz.memo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.biz.memo.domain.MemoVO;
import com.biz.memo.repository.MemoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MemoVO.class},version=1,exportSchema = false)
abstract public class MemoDataBase extends RoomDatabase {
    /*
    데이터베이스 INSTANCE가 생성이 되면서 MemoDao interface를 가져다가 사용할수 있는 class 생성을 한다.
     */
    abstract public MemoDao getMemoDao();

    public static final ExecutorService dbWriterThread=Executors.newFixedThreadPool(3);
    /*
    Database를 생성하는 클래스를 싱글톤으로 선언하기위해 외부에서 접근하는 변수 선언
    앞으로 실행할(생성할) thread를 위한 Context 정보를 담을 객체를
    미리 비어있는 상태로 생성을 해두고 필요할때 공급하는 용도
     */
    private static  volatile MemoDataBase INSTANCE;
    public static MemoDataBase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (MemoDataBase.class){
                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                        MemoDataBase.class,"memo.dbf"
                ).build();
            }
        }
        return INSTANCE;
    }
}
