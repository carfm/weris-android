package sv.com.weris.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manojbhadane.CardType;

import java.util.List;

import sv.com.weris.R;
import sv.com.weris.model.entity.CardClient;
import sv.com.weris.model.interfaces.OnItemClickListener;

import static android.view.View.GONE;


public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.MyViewHolder> {
    Context context;
    private List<CardClient> ofertasList;
    private final OnItemClickListener listener;
    private OnPaymentCardEventListener mListener;
    private static final char space = ' ';

    public TarjetaAdapter(List<CardClient> ofertasList, Context context, OnItemClickListener listener,OnPaymentCardEventListener mListener) {
        this.ofertasList = ofertasList;
        this.context = context;
        this.listener = listener;
        this.mListener = mListener;
    }

    public void updateData(List<CardClient> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @NonNull
    @Override
    public TarjetaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mis_tarjetas, viewGroup, false);
        return new TarjetaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TarjetaAdapter.MyViewHolder holder, final int position) {
        CardClient item = ofertasList.get(position);
        holder.mEdtCardNumber.setText(item.getRx());
        holder.mSpinnerMonth.setText(item.getRy());
        holder.mSpinnerYear.setText(item.getRy1());
        holder.mTxtCardTitle.setText(item.getNombre());
        holder.mEdtCvv.setText(item.getRz());
        holder.bind(item, listener);
        holder.bindAction(item,mListener,position);
    }

    @Override
    public int getItemCount() {
        return ofertasList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private Button mBtnSubmit;
        private ImageView mImgCard;
        private EditText mTxtCardTitle;
        private Typeface RegularTypeFace;
        private EditText mEdtCardNumber, mEdtCvv;
        private EditText mSpinnerMonth, mSpinnerYear;
        private  ImageView mImgCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgCard = itemView.findViewById(R.id.imgCard);
            mBtnSubmit = itemView.findViewById(R.id.btnDone);
            mSpinnerYear = itemView.findViewById(R.id.spryear);
            mSpinnerMonth = itemView.findViewById(R.id.sprmonth);
            mEdtCvv = itemView.findViewById(R.id.edtCvvNumber);
            mTxtCardTitle = itemView.findViewById(R.id.txtCardTitle);
            mEdtCardNumber = itemView.findViewById(R.id.edtCardNumber);
            mImgCancel = itemView.findViewById(R.id.iconCancel);

            RegularTypeFace = Typeface.createFromAsset(context.getAssets(), "arlrdbd.ttf");


            mBtnSubmit.setTypeface(RegularTypeFace);
            mSpinnerYear.setTypeface(RegularTypeFace);
            mSpinnerMonth.setTypeface(RegularTypeFace);
            mEdtCardNumber.setTypeface(RegularTypeFace);
            ((TextView) itemView.findViewById(R.id.txtCardTitle)).setTypeface(RegularTypeFace);
            ((EditText) itemView.findViewById(R.id.edtCvvNumber)).setTypeface(RegularTypeFace);


            mEdtCardNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0 && (s.length() % 5) == 0) {
                        final char c = s.charAt(s.length() - 1);
                        if (space == c) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
                    if (s.length() > 0 && (s.length() % 5) == 0) {
                        char c = s.charAt(s.length() - 1);
                        if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                            s.insert(s.length() - 1, String.valueOf(space));
                        }
                    }
                    showCard(s.toString(),mImgCard);
                }
            });

            mSpinnerMonth.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    String strEnteredVal = s.toString();

                    if (!strEnteredVal.equals("")) {
                        int num = Integer.parseInt(strEnteredVal);
                        if (num > 13) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });





        }

        public void bind(final CardClient item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });




        }

        public void bindAction(final CardClient item, final OnPaymentCardEventListener listener,final Integer posicion) {
            mImgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onCancelClick(item,posicion);
                }
            });

            mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Entro en el detalle");
                    if (mSpinnerMonth.getText().length() > 0) {
                        if (mSpinnerYear.getText().length() > 0) {
                            if (mEdtCardNumber.getText().length() == 19) {
//                            if (isValidCardNumber(mEdtCardNumber.getText().toString())) {
                                if (mEdtCvv.getText().length() == 3) {
                                    listener.onCardDetailsSubmit(mSpinnerMonth.getText().toString(), mSpinnerYear.getText().toString(), mEdtCardNumber.getText().toString(), mEdtCvv.getText().toString(),item);
                                } else {
                                    listener.onError("Ingrese un CVV valido");
                                }
//                            } else {
//
//                                    onError("Enter valid card number");
//                            }
                            } else {
                                listener.onError("Ingrese un numero de tarjeta valido");
                            }
                        } else {
                            listener.onError("Ingrese un año valido");
                        }
                    } else {
                        listener.onError("Ingrese un mes valido");
                    }
                }
            });
        }
    }

    private void showCard(String cNumber,ImageView mImgCard) {
        String cardNumber = cNumber.replaceAll("\\s+", "");
        if (CardType.detect(cardNumber) == CardType.MASTERCARD) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mastercard));
        } else if (CardType.detect(cardNumber) == CardType.VISA) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_visa));
        } else if (CardType.detect(cardNumber) == CardType.AMERICAN_EXPRESS) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_american_express));
        } else if (CardType.detect(cardNumber) == CardType.DISCOVER) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_discover));
        } else if (CardType.detect(cardNumber) == CardType.JCB) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_jcb));
        } else if (CardType.detect(cardNumber) == CardType.CHINA_UNION_PAY) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unionpay));
        } else if (CardType.detect(cardNumber) == CardType.DINERS_CLUB) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_credit_card));
        } else if (CardType.detect(cardNumber) == CardType.UNKNOWN) {
            mImgCard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_credit_card));
        }
    }

    public void setCardTitle(String cardTitle, TextView mTxtCardTitle) {
        if (cardTitle != null && cardTitle.length() == 0)
            mTxtCardTitle.setVisibility(GONE);
        mTxtCardTitle.setText(cardTitle);
    }

    public void setSubmitButtonText(String submitButtonText,Button mBtnSubmit) {
        if (submitButtonText == null)
            mBtnSubmit.setText("Done");
        else if (submitButtonText.length() == 0) {
            mBtnSubmit.setText("Done");
        } else {
            mBtnSubmit.setText(submitButtonText);
        }
    }

    public boolean isValidCardNumber(String cNumber) {
        int sum = 0;
        boolean alternate = false;
        String cardNumber = cNumber.replaceAll("\\s+", "");
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public interface OnPaymentCardEventListener {
        void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv,CardClient item);

        void onError(String error);

        void onCancelClick(CardClient item,Integer posicion);
    }


}
