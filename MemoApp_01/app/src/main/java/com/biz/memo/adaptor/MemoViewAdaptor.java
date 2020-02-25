package com.biz.memo.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biz.memo.R;
import com.biz.memo.domain.MemoVO;

import java.util.List;

public class MemoViewAdaptor extends RecyclerView.Adapter {
    //삭제 버튼에 사용할 이벤트 인터페이스를 하나 생성하고
    // 내용이 없는 이벤트 method를 선언해 두었다.
    public interface  OnDeleteButtonClickListner{
        void onDeleteButtonClicked(MemoVO memoVO);
    }
    private Context context=null;
    private List<MemoVO> memoList=null;
    private LayoutInflater layoutInflater;

    //삭제 버튼 이벤트를 저장할 객체 변수를 선언하고
    private OnDeleteButtonClickListner deleteBtnClick;

    //삭제 버튼 이벤트의 본체를 외부로부터 주입(전달)받을수 있는 setter를 선언.
    public void setDeleteBtnClick(OnDeleteButtonClickListner event){
        this.deleteBtnClick=event;
    }

    /*
    MainActivity에서 MemoViewAdaptor를 만들때 Context와 memoList를 주입할 생성자
     */
    public MemoViewAdaptor(Context context, List<MemoVO> memoList) {
        // 만약 context, list 생성자로 ViewAdapter를 생성하면
        //memoList만 여기에서 로컬객체에 등록을 하고
        //context 변수 값은 context 생성자로 toss하여 그곳에서 layoutinfalter를
        //초기화 하도록 코드를 단일화 한다.
        this(context);
        this.memoList = memoList;
        layoutInflater=LayoutInflater.from(context);
    }
    public MemoViewAdaptor(Context context) {
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }
    public void setMemoList(List<MemoVO> memoList){
        // 외부에서 list를 주입받고 recyclerview에 세팅
        this.memoList=memoList;

        // recyclerview에게 알람
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        memo_item.xml 파일을 가져와서 view객체로 생성(확장)하기
         */
        //View view= LayoutInflater.from(context).inflate(R.layout.memo_item,parent,false);
        View view=layoutInflater.inflate(R.layout.memo_item,parent,false);
        MemoHolder holder=new MemoHolder(view);
        return holder;
    }

    /*
       memo_item.xml에 설정한 여러가지 view들을 사용할수 있도록 초기화 하는 과정
        */
    class MemoHolder extends RecyclerView.ViewHolder{
        public TextView item_view_time;
        public TextView item_view_date;
        public TextView item_view_text;
        public Button item_btn_delete;
        public MemoHolder(@NonNull View itemView) {
            super(itemView);
            item_view_time=itemView.findViewById(R.id.item_time);
            item_view_date=itemView.findViewById(R.id.item_date);
            item_view_text=itemView.findViewById(R.id.item_text);
            item_btn_delete=itemView.findViewById(R.id.item_delete);
        }
    }

    /*
     memoList의 개수만큼 생성되어서 화면에 표시될때 반복문으로 호출되는 메서드
     반복문이 호출되면서 몇번째 데이터인가를 position 변수에 주입해준다.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*
        RecyclerView.ViewHolder를 MemoHolder로 형변환 하여
        MemoHolder에 직접 접근할수 있도록 한다.
         */
        MemoHolder memoHolder=(MemoHolder)holder;

        /*
        memoList의 각 아이템 요소를 한개씩 읽어서 TextView setText() 를 이용해서 문자열을 채워넣어 준다.
         */
        memoHolder.item_view_date.setText(memoList.get(position).getM_date());
        memoHolder.item_view_time.setText(memoList.get(position).getM_time());
        memoHolder.item_view_text.setText(memoList.get(position).getM_text());
        /*memoHolder.item_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        // 화면에 보이는 아이템만 삭제하는 이벤트 코드
        //ViewAdapter에서 memoViewModel을 가져와서 연결한후 DB를 처리해야한다.
        // 그렇게 하기에는 여러가지 퍼포먼스에서 문제가 발생할수 있다.
        //이벤트를 MainActivity로 옮겨서 설정을 한후 가져와서 처리를 해줘야 한다.
        /*memoHolder.item_btn_delete.setOnClickListener((view)-> {
            memoList.remove(position);
            notifyDataSetChanged();;
        }
        );*/
        memoHolder.item_btn_delete.setOnClickListener(v->deleteBtnClick.onDeleteButtonClicked(memoList.get(position)));
    }

    @Override
    public int getItemCount() {
        return memoList!=null?memoList.size():0;
    }


}
