package com.dearjacky;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alorma.timeline.TimelineView;

import java.util.Calendar;
import java.util.List;

class DataPointAdapter extends ArrayAdapter<DataPointJacky> {
    private final LayoutInflater layoutInflater;
    private int listSize;
    private Context context;
    private static MyClickListener myClickListener = new MyClickListener() {
        @Override
        public void onItemClick(int position, View v) {
            //
        }
    };
//    private final Color[] colors = [getResources().];

    public DataPointAdapter(Context context, List<DataPointJacky> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
        listSize = objects.size();
        this.context = context;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_main, parent, false);
            viewHolder = new ViewHolderItem(convertView);
            viewHolder.leftText = (TextView) convertView.findViewById(R.id.event_name);
            viewHolder.leftText.setText("Hi");
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            viewHolder.timeline = (TimelineView) convertView.findViewById(R.id.timeline);
            convertView.setTag(viewHolder);
            viewHolder.cv = (CardView) convertView.findViewById(R.id.cv);
            viewHolder.tail = (View) convertView.findViewById(R.id.tail);
            viewHolder.timeStamp = (TextView) convertView.findViewById(R.id.time_stamp);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        DataPointJacky dataPoint = getItem(position);

//        String[] titles = {"Played basketball with friends",
//                "Ate a pie",
//                "Hung out with my dog and went to the park to play fetch",
//                "Got a good amount of sleep",
//                "Reddit",
//                "Pie.",
//                "Played a game of frisbee and finally was able to throw it that way where you flick your wrist instead of winding up with your whole arm",
//                "hamster died",
//                "Got a bad grade."};
////        viewHolder.leftText.setText(events.getName());
//        viewHolder.leftText.setText(titles[(int)(Math.random()*9)]);
        viewHolder.note = dataPoint.note;
        viewHolder.mood = dataPoint.mood;
        viewHolder.intensity = dataPoint.intensity;
        viewHolder.millis = dataPoint.timestamp;

        viewHolder.leftText.setText(dataPoint.note);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataPoint.timestamp);
        String[] days = {"Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};

        viewHolder.day.setText(days[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        viewHolder.number.setText("" + calendar.get(Calendar.DAY_OF_MONTH));
        String hour_of_day = "" + calendar.get(Calendar.HOUR_OF_DAY);
        String minute = "" + calendar.get(Calendar.MINUTE);
        if(calendar.get(Calendar.HOUR_OF_DAY) < 10)
            hour_of_day = "0" + hour_of_day;
        if(calendar.get(Calendar.MINUTE) < 10)
            minute = "0" + minute;
        viewHolder.timeStamp.setText(hour_of_day + ":" + minute);

//        if(Math.random()*5 < 1){
//            String[] days = {"Sun", "Mon", "Tues", "Wed", "Fri", "Sat", "Sun" };
//            int day = (int)(Math.random()*31 +1);
//            viewHolder.day.setText(days[day%7]);
//            viewHolder.number.setText(""+day);
//
//        }
        System.out.println(listSize);
        System.out.println(position);
        if(position == 0)
            viewHolder.timeline.setTimelineType(TimelineView.TYPE_START);
        else if(position < listSize - 1)
            viewHolder.timeline.setTimelineType(TimelineView.TYPE_MIDDLE);
        else
            viewHolder.timeline.setTimelineType(TimelineView.TYPE_END);
        viewHolder.timeline.setTimelineAlignment(TimelineView.ALIGNMENT_DEFAULT);

        int color = 0;
        int tail = R.drawable.tail;
        if (dataPoint.mood.equals("happy")) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorExcited);
            tail = R.drawable.tail_excited;
        }else if(dataPoint.mood.equals("ok")) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorCalm);
            tail = R.drawable.tail_happy;
        }else if(dataPoint.mood.equals("sad")) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorDepressed);
            tail = R.drawable.tail_sad;
        }else if(dataPoint.mood.equals("angry")) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorAngry);
            tail = R.drawable.tail_angry;
        }

        viewHolder.cv.setCardBackgroundColor(color);
        viewHolder.tail.setBackgroundResource(tail);
        viewHolder.timeline.setIndicatorColor(color);
        viewHolder.timeline.setInternalColor(color);

        return convertView;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View tail;
        //public Item currentItem;
        public TextView day;
        public TextView number;
        public TimelineView timeline;
        public TextView leftText;
        public TextView timeStamp;
        public CardView cv;
        public String note;
        public String mood;
        public int intensity;
        public long millis;

        public ViewHolderItem(View v) {
            super(v);
            tail = v;
            tail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
            System.out.println("timeStamp: " + timeStamp.getText().toString());
            Intent intent = new Intent(v.getContext(), EditResponseActivity.class);
            intent.putExtra("millis", String.valueOf(this.millis));
            intent.putExtra("note", this.note);
            intent.putExtra("mood", this.mood);
            intent.putExtra("intensity", String.valueOf(this.intensity));
            v.getContext().startActivity(intent);
        }
    }

    public void onBindViewHolder(ViewHolderItem viewHolder, int i) {
        viewHolder.leftText.setText(getItem(i).note);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
