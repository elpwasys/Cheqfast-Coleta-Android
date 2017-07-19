package cheqfast.gfin.wasys.com.br.coleta.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import br.com.wasys.library.adapter.ListAdapter;
import br.com.wasys.library.utils.DateUtils;
import br.com.wasys.library.utils.FieldUtils;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.model.ProcessoModel;

/**
 * Created by pascke on 28/04/16.
 */
public class ProcessoAdapter extends ListAdapter<ProcessoModel> {

    public ProcessoAdapter(Context context, List<ProcessoModel> rows) {
        super(context, rows);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        }
        else {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.list_item_processo, null);
            holder.idTextView = (TextView) view.findViewById(R.id.text_nome);
            holder.dataTextView = (TextView) view.findViewById(R.id.text_data);
            holder.statusTextView = (TextView) view.findViewById(R.id.text_status);
            holder.coletaTextView = (TextView) view.findViewById(R.id.text_view_coleta);
            holder.statusImageView = (ImageView) view.findViewById(R.id.image_status);
            holder.coletaImageView = (ImageView) view.findViewById(R.id.image_view_coleta);
            view.setTag(holder);
        }
        ProcessoModel processo = mRows.get(position);

        FieldUtils.setText(holder.idTextView, processo.id);

        holder.statusTextView.setText(processo.status.stringRes);
        holder.statusImageView.setImageResource(processo.status.drawableRes);

        holder.coletaTextView.setText(null);
        holder.coletaImageView.setImageBitmap(null);
        if (processo.coleta != null) {
            holder.coletaTextView.setText(processo.coleta.stringRes);
            holder.coletaImageView.setImageResource(processo.coleta.drawableRes);
        }

        Date today = DateUtils.truncate(new Date());
        Date dataCriacao = DateUtils.truncate(processo.dataCriacao);

        Context context = view.getContext();
        if (today.equals(dataCriacao)) {
            FieldUtils.setText(holder.dataTextView, DateUtils.format(processo.dataCriacao, "HH:mm"));
        } else if (DateUtils.getDaysBetween(today, dataCriacao) == 1) {
            FieldUtils.setText(holder.dataTextView, context.getString(R.string.ontem));
        } /*else if (DateUtils.getDaysBetween(today, dataCriacao) == 2) {
            FieldUtils.setText(holder.dataTextView, context.getString(R.string.anteontem));
        }*/ else {
            FieldUtils.setText(holder.dataTextView, DateUtils.format(processo.dataCriacao, "dd/MM/yy"));
        }

        return view;
    }

    static class ViewHolder {
        TextView idTextView;
        TextView dataTextView;
        TextView statusTextView;
        TextView coletaTextView;
        ImageView statusImageView;
        ImageView coletaImageView;
    }
}