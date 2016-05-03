package com.dearjacky;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alorma.timeline.TimelineView;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Matcher;

class EventsAdapter extends ArrayAdapter<Events> {
    private final LayoutInflater layoutInflater;
//    private final Color[] colors = [getResources().];

    public EventsAdapter(Context context, List<Events> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_main, parent, false);
            viewHolder = new ViewHolderItem();
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

        Events events = getItem(position);

        String[] titles = {"Played basketball with friends",
                "Ate a pie",
                "Hung out with my dog and went to the park to play fetch",
                "Got a good amount of sleep",
                "Reddit",
                "Pie.",
                "Played a game of frisbee and finally was able to throw it that way where you flick your wrist instead of winding up with your whole arm",
                "hamster died",
                "Got a bad grade."};
//        viewHolder.leftText.setText(events.getName());
        viewHolder.leftText.setText(titles[(int)(Math.random()*9)]);

        if(Math.random()*5 < 1){
            String[] days = {"Sun", "Mon", "Tues", "Wed", "Fri", "Sat", "Sun" };
            int day = (int)(Math.random()*31 +1);
            viewHolder.day.setText(days[day%7]);
            viewHolder.number.setText(""+day);

        }
        viewHolder.timeline.setTimelineType(events.getType());
        viewHolder.timeline.setTimelineAlignment(events.getAlignment());

        int color = 0;
        int tail = R.drawable.tail;
        if (events.getMood() == 0) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorExcited);
            tail = R.drawable.tail_excited;
        }else if(events.getMood() == 1) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorCalm);
            tail = R.drawable.tail_happy;
        }else if(events.getMood() == 2) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorDepressed);
            tail = R.drawable.tail_sad;
        }else if(events.getMood() == 3) {
            color = ContextCompat.getColor(parent.getContext(), R.color.colorAngry);
            tail = R.drawable.tail_angry;
            Glide.with(getContext()).load(R.drawable.angry).into(viewHolder.timeline);
            viewHolder.timeline.setIndicatorSize(
            30);
//            viewHolder.timeline.
        }

        viewHolder.cv.setCardBackgroundColor(color);
        viewHolder.tail.setBackgroundResource(tail);
//        viewHolder.timeline.setIndicatorColor(color);
//        viewHolder.timeline.setInternalColor(color);




        return convertView;
    }

    static class ViewHolderItem {
        TextView day;
        TextView number;
        TimelineView timeline;
        TextView leftText;
        TextView timeStamp;
        CardView cv;
        View tail;

    }
}
