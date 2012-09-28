package ru.spb.itolia.redmine.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.api.beans.RedmineSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 28.09.12
 * Time: 17:00
 */
public class HostsAdapter extends ArrayAdapter<RedmineSession> {
    private Context context;
    private List<RedmineSession> sessions;

    public HostsAdapter(Context context, List<RedmineSession> sessions) {
        super(context, R.layout.hosts_row, R.id.host_label, sessions);
        this.sessions = sessions;
        this.context = context;
    }

    class ViewHolder {
        public TextView label;
        public TextView host;
        public TextView username;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.hosts_row, null);
            ViewHolder viewHolder = new ViewHolder();
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.host = (TextView) rowView.findViewById(R.id.address);
        holder.label = (TextView) rowView.findViewById(R.id.host_label);
        holder.username = (TextView) rowView.findViewById(R.id.username);
        RedmineSession p = (RedmineSession) sessions.get(position);   //TODO change for RedmineSession
        holder.host.setText(p.getAddress());
        holder.label.setText(p.getLabel());
        holder.username.setText(p.getUsername());
        return rowView;
    }
}