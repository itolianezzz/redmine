package ru.spb.itolia.redmine.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.api.beans.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 28.09.12
 * Time: 17:14
 */
public class ProjectsAdapter extends ArrayAdapter<Project> {
    private Context context;
    private List<Project> projects;
    //private LayoutInflater mInflater;


    public ProjectsAdapter(Context context, List<Project> objects) {
        super(context, R.layout.project_row, objects);
        this.projects = objects;
        this.context = context;
    }

    class ViewHolder { // TODO fix static
        public TextView projectName;
        public TextView projectDesc;
        public TextView createdOn;
        public TextView updatedOn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.project_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.projectName = (TextView) rowView
                    .findViewById(R.id.projectName);
            viewHolder.projectDesc = (TextView) rowView
                    .findViewById(R.id.projectDesc);
            viewHolder.createdOn = (TextView) rowView
                    .findViewById(R.id.projectCreatedOn);
            viewHolder.updatedOn = (TextView) rowView
                    .findViewById(R.id.projectUpdateOn);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Project p = projects.get(position);
        holder.projectName.setText(p.getName());
        holder.projectDesc.setText(p.getDescription());
        holder.createdOn.setText(p.getCreated_on());
        holder.updatedOn.setText(p.getUpdated_on());

        return rowView;
    }
}