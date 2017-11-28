package ca.bcit.newwest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.bcit.newwest.model.Neighbourhood;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 27, 2017
 */

public class NeighbourhoodAdapter extends ArrayAdapter<Neighbourhood> {

    public NeighbourhoodAdapter(Context context, List<Neighbourhood> areas) {
        super(context, 0, areas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Neighbourhood neighbourhood = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.neighbourhood_list_row, parent, false);
        }

        TextView neighbourhoodView = convertView.findViewById(R.id.textView_list_neighbourhoodName);
        neighbourhoodView.setText(neighbourhood.getName());

        return convertView;
    }
}
