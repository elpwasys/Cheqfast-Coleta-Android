package cheqfast.gfin.wasys.com.br.coleta.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.wasys.library.utils.AndroidUtils;
import cheqfast.gfin.wasys.com.br.coleta.R;
import cheqfast.gfin.wasys.com.br.coleta.model.CampoGrupoModel;
import cheqfast.gfin.wasys.com.br.coleta.model.CampoModel;

/**
 * Created by pascke on 25/06/17.
 */

public class AppGroupReadonlyInputLayout extends LinearLayout {

    private CampoGrupoModel mGrupo;

    public AppGroupReadonlyInputLayout(Context context) {
        super(context);
    }

    public AppGroupReadonlyInputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppGroupReadonlyInputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AppGroupReadonlyInputLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setGrupo(CampoGrupoModel grupo) {
        mGrupo = grupo;
        create();
    }

    private void clear() {
        removeAllViews();
    }

    private void create() {
        clear();
        createHeader();
        createFields();
    }

    private void createHeader() {

        Context context = getContext();

        TextView textView = new TextView(context);
        textView.setText(StringUtils.capitalize(mGrupo.nome));

        int margin = AndroidUtils.toPixels(context, 4f);
        int marginTop = AndroidUtils.toPixels(context, 16f);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, marginTop, margin, margin);
        textView.setLayoutParams(params);

        TextViewCompat.setTextAppearance(textView, android.R.style.TextAppearance_Small);

        int color = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        textView.setTextColor(color);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_section, null);
        textView.setBackground(drawable);

        addView(textView);
    }

    private void createFields() {
        if (CollectionUtils.isNotEmpty(mGrupo.campos)) {
            for (CampoModel campo : mGrupo.campos) {
                createLabel(campo);
                createValue(campo);
            }
        }
    }

    private void createLabel(CampoModel campo) {
        Context context = getContext();
        int margin = AndroidUtils.toPixels(context, 4f);
        int marginTop = AndroidUtils.toPixels(context, 8f);
        String nome = StringUtils.capitalize(campo.nome.toLowerCase());
        TextView textlView = new TextView(context);
        textlView.setText(nome);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, marginTop, margin, margin);
        textlView.setLayoutParams(layoutParams);
        TextViewCompat.setTextAppearance(textlView, android.R.style.TextAppearance_Small);
        int color = ResourcesCompat.getColor(getResources(), R.color.grey_600, null);
        textlView.setTextColor(color);
        addView(textlView);
    }

    private void createValue(CampoModel campo) {
        Context context = getContext();
        int margin = AndroidUtils.toPixels(context, 4f);
        int marginTop = AndroidUtils.toPixels(context, 8f);
        TextView textlView = new TextView(context);
        textlView.setText(campo.valor);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, marginTop, margin, margin);
        textlView.setLayoutParams(layoutParams);
        TextViewCompat.setTextAppearance(textlView, android.R.style.TextAppearance_Small);
        int color = ResourcesCompat.getColor(getResources(), R.color.grey_400, null);
        textlView.setTextColor(color);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_field_readonly, null);
        textlView.setBackground(drawable);
        addView(textlView);
    }
}
