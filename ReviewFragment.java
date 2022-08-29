package swastika.rm.app.view.fragments;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.FileProvider;

import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.digio.in.esign2sdk.Digio;
import com.digio.in.esign2sdk.DigioConfig;
import com.digio.in.esign2sdk.DigioEnvironment;
import com.digio.in.esign2sdk.DigioServiceMode;
import com.github.aakira.expandablelayout.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.common.net.MediaType;
import com.itextpdf.text.pdf.PdfReader;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;
import com.timqi.sectorprogressview.SectorProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import hari.bounceview.BounceView;
import id.zelory.compressor.Compressor;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import swastika.rm.app.BuildConfig;
import swastika.rm.app.R;
import swastika.rm.app.add_family_member.FirstCustomerDetailsForFamilyMember;
import swastika.rm.app.custom_esign.CustomEsignActivity;
import swastika.rm.app.custom_esign.EmudraActivity;
import swastika.rm.app.model.BrokerageModel;
import swastika.rm.app.model.FinalApplicationForm;
import swastika.rm.app.model.FormData;
import swastika.rm.app.model.TB_Segments;
import swastika.rm.app.model.UserBasicInfo;
import swastika.rm.app.model.UserIdentity;
import swastika.rm.app.model.userDocuments;
import swastika.rm.app.modification.ModificationFieldModle;
import swastika.rm.app.modification.ModificationFieldScreenActivity;
import swastika.rm.app.modification.ModificationFirstScreen;
import swastika.rm.app.modification.Nominee_Modification;
import swastika.rm.app.network.VolleyMultipartRequest;
import swastika.rm.app.network.getResponse;
import swastika.rm.app.presentation.AccountOpeningFormActivity;
import swastika.rm.app.utility.ApiConfig;
import swastika.rm.app.utility.AppConfig;
import swastika.rm.app.utility.AppHelper;
import swastika.rm.app.utility.AppInfo;
import swastika.rm.app.utility.RemarkUtility;
import swastika.rm.app.utility.ScanResultAadhar;
import swastika.rm.app.utility.ServerResponse;
import swastika.rm.app.utility.ShowMessage;
import swastika.rm.app.utility.UtilsClass;
import swastika.rm.app.utility.WebServices;
import swastika.rm.app.view.activity.FullScreenImageView;
import swastika.rm.app.view.activity.InputFilterMinMax;
import swastika.rm.app.view.activity.MainHomeActivity;
import swastika.rm.app.view.activity.SaveClientCode;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.WHITE;
import static android.os.Build.VERSION.SDK_INT;
import static org.chromium.base.ContextUtils.getApplicationContext;
import static swastika.rm.app.utility.AppInfo.token_PREFS_NAME;
import static swastika.rm.app.view.activity.MainHomeActivity.activity;

/*import com.nsdl.egov.esignaar.NsdlE  signActivity;*/
//http://54.80.63.129/api/AdditionalDetails/getAllAdditionalDetails?ekycApplicationId=775
public class ReviewFragment extends Fragment {
    //    vaibhave.k@trading
    //    bells.com
    TextView submit_btn_getLoan_detail;
    ImageView testImageIV;
    public ArrayList<BrokerageModel> Cash_Intraday_ArrayList;
    public ArrayList<String> Cash_Intraday_Array;
    public ArrayList<String> Cash_Intraday_Array_description;
    public String Cash_intraday_MODULE_NO = "111";
    public String Cash_intraday_ONESIDEPER = "";
    public String Cash_intraday_COMPANY_CODE = "";

    JSONArray NSE_CASH_INTRADAY_BrokerageDetail = null;
    boolean checknominee=false;
    RecyclerCustomAdapter_Delivery stringArrayAdapter_brokerage_Delivery;
    RecyclerCustomAdapter_Intraday stringArrayAdapter_brokerage_Intraday;
    RecyclerCustomAdapter_DerivativeOption stringArrayAdapter_DerivativeOption;
    RecyclerCustomAdapter_DerivativeFuture stringArrayAdapter_DerivativeFuture;

    RecyclerCustomAdapter_CurrencyDerivativeFuture stringArrayAdapter_currencyDerivativeFuture;
    RecyclerCustomAdapter_CurrencyDerivativeOption stringArrayAdapter_currencyDerivativeOption;

    RecyclerCustomAdapter_CommoditiyDerivativeOption stringArrayAdapter_commoditiyDerivativeOption;
    RecyclerCustomAdapter_CommoditiyDerivativeFuture stringArrayAdapter_commoditiyDerivativeFuture;


    public static Dialog brokerage_dialog;
    public ArrayList<BrokerageModel> Cash_Delivery_ArrayList;
    public ArrayList<String> Cash_Delivery_Array;
    public ArrayList<String> Cash_Delivery_Array_description;

    public String Cash_Delivery_MODULE_NO = "1013";
    public String Cash_Delivery_DELIVERYPER = "";
    public String Cash_Delivery_COMPANY_CODE = "";

    public ArrayList<BrokerageModel> derivative_future_ArrayList;
    public ArrayList<String> derivative_future_Array;

    public String derivative_future_MODULE_NO = "102";
    public String derivative_future_COMPANY_CODE = "";

    public ArrayList<BrokerageModel> derivatives_options_ArrayList;
    public ArrayList<String> derivatives_options_Array;

    public String derivatives_options_MODULE_NO = "944357";
    public String derivatives_options_COMPANY_CODE = "";

    public ArrayList<BrokerageModel> currency_derivatives_futures_ArrayList;
    public ArrayList<String> currency_derivatives_futures_Array;

    public String currency_derivatives_futures_MODULE_NO = "102";
    public String currency_derivatives_futures_COMPANY_CODE = "";

    public ArrayList<BrokerageModel> currency_derivatives_options_ArrayList;
    public ArrayList<String> currency_derivatives_options_Array;

    public String currency_derivatives_options_MODULE_NO = "446838";
    public String currency_derivatives_options_COMPANY_CODE = "";

    public ArrayList<BrokerageModel> commodity_derivatives_futures_ArrayList;
    public ArrayList<String> commodity_derivatives_futures_Array;

    public String commodity_derivatives_futures_MODULE_NO = "102";
    public String commodity_derivatives_futures_COMPANY_CODE = "";

    public ArrayList<BrokerageModel> commodity_derivatives_options_ArrayList;
    public ArrayList<String> commodity_derivatives_options_Array;

    public String commodity_derivatives_options_MODULE_NO = "1119";
    public String commodity_derivatives_options_COMPANY_CODE = "";

    String nomineeId1="",nomineeId2="",nomineeId3="";
    LinearLayout client_code_section, client_code_editable_section, main_nominee_layoutsss;
    TextView userClientCode_edt_lable, userClientCode_edt, userClientCode_pencil, userClientCode_Submit;
    EditText userClientCode_edtitext;
    boolean isLoanAmountFilled,
            isLoanTenureFilled, isChequeFirstFilled, isBankFirstFiiled,
            isBranchFirstFilled, isChequeSecondFilled, isBankSecondfilled, isBranchSecondfilled;
    private AlertDialog.Builder pdfAlertDialog;
    String final_edt_clientcode = "";
    File PDFfile, chequeFileOnePdf, chequeFileTwoPdf, bankStatementPdf;
    boolean isChequeOneUploaded, isChequeTwoUploaded, isbankStatementUploaded, isPasswordProtected;
    File chequeFileOne, chequeFileTwo, bankStatementFile;
    String DocumentType = "";
    String titleDoc;

    String bankStatementDocUrl, chequeOneDocUrl, chequeTwoDocUrl, eKycId, customerId, additionalDetailsId, additionalDetailsIdd, loanAmount, loanTenurePeriod, pdc_udc_number,
            chequeNumberFirst, bankNumberFirst, branchFirst, chequeNumberSecond, bankNumberSecond, branchSecond;
    boolean eMudhraSignature;
    String eMudhraReferenceNumber;
    String eMudhraAccountStatus;
    String customerFirstName;
    String UserPrefix = "";
    int PrefixDigit = 6;
    String IsEditableClientCode = "";
    File pdfFile = null;
    //EMUDRA---Esign/////
    String base64_data;
    String emudraPdfPath = "";
    String filledFormFileName = "";
    CheckBox pdf_terms_check;
    public static String[] stateIdArray;
    private ProgressDialog File_download_dialog;
    private String customerName;
    private String newCustomerFullName;

    private static final int MEGABYTE = 1024 * 1024;
    public static boolean documentUploaded = false;
    public static int REQUEST_CODE_DOC = 4567;
    public static boolean IsBoolean = false;
    public static BottomSheetBehavior bottomSheetBehaviorAdditionDoc,
            bottomSheetBehaviorBrockrage,
            bottomSheetBehaviorAddNominee, bottomSheetBehaviorAddNominees;
    public static String[] stateArray;
    public static Button btnSubmit;
    public static TextView btnEdit, tvNewEmailForEsign;
    public static EditText etEmailForEsign, etNewEmailForEsign;
    public static RelativeLayout
            review_applicationMainLayout,
            error_with_aadhar,
            congratulationsLayout,
            aadhar_not_linked,
            additional_doc,
            reviewWebView,
            rl_uploadBrokrage,
            physicalDocUpload, notelayout;
    public Uri photoURI;
    public String imageFilePath;
    int time = 30;
    String stateId;
    boolean eSign;
    ImageView tempHoldImageView, brokageImg, varificationVideo;
    File aadharCardFileBack;
    /* Additional Detail Ids */
    ACProgressFlower acProgressFlower;
    LinearLayout nominationsLayout_new;
    ProgressDialog progressDialog;
    SwitchCompat minorToggle, minorToggle_new, minorToggle_news;
    LinearLayout subBroker_form_layout, getLoanDetailLayout,
            nominee_input_box, nominee_input_box_new, incomeDocLayout,
            nominee_address_different, nominee_address_different_new, lMainGardian, lMainGardian_new, llSuporttivedoc,
            nominee_address_different_news,nominee_address_different_news2,nominee_address_different_news3;
    TextView submit_btn_additional_detail, brokTxt, brokrafeNotes, mobileLinkText, howToLonkTextView;
    String brpkrageDescription = "", VideoUrl = "", VideoExtension = "", MainEmail = "";
    RadioGroup declare_mobile_number_belong_to,
            nominee_identification_option, nominee_identification_option_new, address_of_nominee, address_of_nominee_new,
            avail_transaction_using_secured, declare_email_belongs_to,
            is_a_us_person, specify_country_residence,
            specify_country_citizenhip, past_actions,
            sub_broker,
            RGaccount, review_contract,
            review_delivery, shareEmail, recieve_annual_report,
            receive_DP_accounts, make_nomination, make_nomination_new, radioGroupdurgesh, sebi_registration_group, instruction_dp_to_recive_every_cradit,
            instruction_dp_to_accept_all_pledge,
            bank_account_as_given_below_through_ECS, basic_services_demat_account_facility;


    RadioButton is_a_us_person_YES,
            is_a_us_person_NO,
            specify_country_residence_INDIA,
            specify_country_residence_OTHER,
            specify_country_citizenhip_INDIA,
            specify_country_citizenhip_OTHER,
            past_actions_NO,
            past_actions_YES,
            sub_broker_NO,
            sub_broker_YES,
            sebi_registration_group_NO,
            sebi_registration_group_YES,
            review_contract_electronically, radio_cdsl, radio_nsdl, radio_other,
            review_contract_physically,
            review_delivery_NO,
            review_delivery_YES,
            shareEmail_YES,
            shareEmail_NO,
            recieve_annual_report_electronically,
            recieve_annual_report_physically,
            recieve_annual_report_both,
            receive_DP_accounts_as_per_sebi,
            receive_DP_accounts_monthly,
            receive_DP_accounts_forthnightly,
            receive_DP_accounts_weekly,
            declare_mobile_number_belong_to_self,
            declare_mobile_number_belong_to_spouse,
            declare_mobile_number_belong_to_child,
            declare_mobile_number_belong_to_parant,
            declare_mobile_email_belongs_to_self,
            declare_mobile_email_belongs_to_spouse,
            declare_mobile_email_belongs_to_child,
            declare_mobile_email_belongs_to_parent,
            avail_transaction_using_secured_YES,
            instruction_dp_to_recive_every_cradit_yes,
            instruction_dp_to_recive_every_cradit_NO,
            instruction_dp_to_accept_all_pledge_YES,
            instruction_dp_to_accept_all_pledge_NO,
            bank_account_as_given_below_through_ECS_YES,
            bank_account_as_given_below_through_ECS_NO,
            basic_services_demat_account_facility_YES,
            basic_services_demat_account_facility_NO,
            avail_transaction_using_secured_NO,
            make_nomination_NO,
            make_nomination_NO_new,
            make_nomination_YES,
            make_nomination_YES_new,
            radionobutton_no,
            radioyesbutton_yes,
            address_of_nominee_same_as_application,
            address_of_nominee_same_as_application_new,

    address_of_nominee_different_from_application,
            address_of_nominee_different_from_application_new,

    nominee_identification_option_photograph,
            nominee_identification_option_photograph_new,
            nominee_identification_option_pan,
            nominee_identification_option_pan_new,
            nominee_identification_option_address_proof,
            nominee_identification_option_address_proof_new,
            nominee_identification_option_proof_of_identity,
            nominee_identification_option_proof_of_identity_new,
            nominee_identification_option_saving_bank,
            nominee_identification_option_saving_bank_new,
            nominee_identification_option_demat_account,
            nominee_identification_option_demat_account_new,
            guardian_photograph,
            guardian_photograph_new,
            guardian_pan,
            guardian_pan_new,
            guardian_address_proof,
            guardian_address_proof_new,
            guardian_proof_of_identity,
            guardian_proof_of_identity_new,
            guardian_saving_bank_account,
            guardian_saving_bank_account_new,
            guardian_demat_account,
            guardian_demat_account_new;

    TextView document_typeNominee_1,document_typeNominee_2,document_typeNominee_3,document_typeNomineeGurdian_1,document_typeNomineeGurdian_2,document_typeNomineeGurdian_3;

    EditText past_action_specification_edt, stock_broker_name,
            sub_broker_name_edt,
            sub_broker_authorised_person_edt,
            sub_broker_exchange_name_edt,
            sub_broker_client_code_edt,
            sub_broker_dues_edt,
            sub_broker_exchange_edt,
            sebi_registration_group_Number_edt,
            nominee_Name_edt,
            edt_firstnominee,
            edtpercentagesharing,
            nominee_Share_edt,
            nominee_Share_edt_new,
            nominee_address_edt,
            nominee_address_edt_new,
            nominee_pincode_edt,
            nominee_pincode_edt_new,
            nominee_city_edt,
            nominee_city_edt_new,
            nominee_relationship_edt,
            nominee_relationship_edt_new,
            nominee_dob_edt,
            nominee_dob_edt_new,
            nominee_mobile_number_edt,
            nominee_mobile_number_edt_new,
            nominee_email_edt,
            nominee_email_edt_new,
            nominee_identification_edt,
            nominee_identification_edt_new,
            date_of_birth_guardian_edt,
            date_of_birth_guardian_edt_new,
            name_of_guardian_edt,
            name_of_guardian_edt_new,
            address_of_guardian_edt,
            address_of_guardian_edt_new,
            mobile_number_of_guardian_edt,
            mobile_number_of_guardian_edt_new,
            email_id_of_guardian_edt,
            email_id_of_guardian_edt_new,
            relationship_of_guardian_edt,
            relationship_of_guardian_edt_new, edittextpincode;
    int REQUEST_CODE = 100;
    String document_id = "", ImgStrAadharFront = "", ImgStrAadharBack = "", ImgStrPAN = "", ImgStrIncome,
            ImgStrAdditional1 = "", ImgStrAdditional2 = "", ImgStrBank1 = "", ImgStrBan2 = "", ImgStrPhoto = "", ImgStrSignature = "", ImgStrVarification;
    ScrollView brokerageScroll;
    Button add_nominee, remove_nominee;
    LinearLayout uploadAdditionalDocLayout, llAddressProof, ll_reviewSubmit, llVarification, add_remove_layout;
    Boolean isCommoditySelected = false;
    Boolean brokerageAlreadyFound = false;
    String stock_delivery_value,
            stock_intraday_value,
            derivatives_futures_value,
            derivatives_options_value,
            currency_derivatives_futures_value,
            currency_derivatives_options_value,
            commodity_derivatives_futures_value,
            commodity_derivatives_options_value, unitStockDelivery_value, unitStockintraday_value,
            unitDerivetivefuture_value, unitDerivetiveoption_value;
    CheckBox checkBoxaddress,gurdianaddresscheckbox,gurdianaddresscheckbox_ofsecondnominee;
    TextView stock_intraday, stock_delivery,
            derivatives_futures, derivatives_options,
            currency_derivatives_futures, currency_derivatives_options,
            commodity_derivatives_futures, commodity_derivatives_options;

    EditText unitStockDelivery, unitStockIntraday, unitDerivetivefuture,
            unitCurrencyoption, unitDerivativeOption, unitCurrencyfuture, unitCommodityfuture, unitCommodityoption,
            turnOverEdit, edtothername;
    String getres_access_token = "";


    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    boolean digioDownloaded = false;
    String digioDocumentId = "";
    String addressproofstr = "";
    int MTFstatusId = 0;
    String MTFstatus = "";
    String IsMTF = "";
    String currentstatus = "";
    String IfscCode = "";
    String AccountHolderName = "";
    String AccountNumber = "";
    String BankName = "";
    boolean esigned;
    String MICR = "";
    String BankState = "";
    String BankCity = "";
    String BankBranch = "";
    String BankZipCode = "";
    String country = "";
    int value=0;
    RelativeLayout rl_addnominelayout;
    LinearLayout ll_nominesecondlayout,ll_nominethirdlayout,toggle_button;

    boolean secondnomineactive=false,thirdnomineactive=false;
    TextView userPanEditText, userDOBEditText, userNameEditText,
            userFNameEditText, userEmailEdit, userMobileEdit, userMNameEditText, userMaritalStatusEditText, userGenderEditText,
            userAddressEditText, userPostcalCodeEditText, userCityEditText, userStateEditText, userIncomeEditText,
            userOccupationEditText, userPolicalPartyEditText, userTradingEditText, userDematEditText,
            userSegmentsEditText, usermtfEditText,
            percentagesharingodnomineeone, addressofnomineone;


    TextView closeBrockrageBox, save_brockrage, userClientCode;
    String AddtioinalOnePDFurl = "", AddtioinalTwoPDFurl = "", BankstatemaneOne = "", BankstatemaneTwo = "", incomePDF = "";
    ImageView additionalImageone, addressProofImage, additionalImagetwo, userAadharFrontImage, userAadharBackImage, userPanImage, userIncomeImage, userBankImage2, userBankImage, userPhotoImage, userSignatureImage;
    String currentStatu = "";
    //    TextView review_confirm_btn;
    TextView userPanTextView, userDOBTextView, userNameTextView, cancel_btn_getLoan_detail,
            userFNameTextView, userEmailText, userMobileText, userMNameTextView, userMaritalStatusTextView, userGenderTextView,
            userAddressTextview, userPostcalCodeTextView, userCityTextView, userStateTextView, userIncomeTextView,
            userOccupationTextView, userPolicalPartyTextView, userTradingTextView, userDematTextView,
            userSegmentsTextView, usermtfTextView;
    // For API
    public static HashMap<String, Object> parameters;
    public static getResponse getRes;
    ImageView account_plus_img,
            facta_plus_img,
            past_action_plus_img,
            authorised_plus_img,
            standing_instruction_plus_img,
            getLoan_plus_img,
            nomination_plus_img,
            nomination_plus_img_new,
            BankBox1Image, uploadCheque2Box2ImageTwo, cheque1fBox1Image;
    LinearLayout account_bottom, facta_bottom,
            getLoanLayout,
            past_action_bottom,
            authorisedPerson_bottom,
            standing_instructiom_bottom,
            getLoan_d_bottom,
            nomination_bottom, nomination_bottom_new, ll_account_plus_img;

    LinearLayout llSAveBrokrage, nomineetabslayout, guardiandetailslayout,guardiandetailslayout_secondNominee,guardiandetailslayout_thirdNominee;
    LinearLayout downloadPDF, ChequeNoFirstLayout, etBankLayout, etNoteHedLayout, etNoteHedFirst, etNoteHedSecond,
            BranchFirstLayout, doclayout, uploadCheque1, uploadCheque1layout, uploadCheque2Layout, uploadBankLayout,
            ChequeNoSecondtLayout, etBankLayoutSecond, BranchSecondLayout;
    EditText etChequeNoFirst, etBankFirst, etBranchFirst, etChequeFirst, etLoanAmount, etLoanTenurePeriod, etBankSecond, etBranchSecond, etchequeSecond;
    Boolean factaBottomOpen = false, pastActionBottomOpen = false,gurdiansaddressselcted=false,gurdiansaddressselcted_ofsecondnominee=false,
            gurdianaddress_ofsecondnominee=false,secondn_Nominee_Address=false,first_Nominee_Address=false,third_Nominee_Address=false,

    authorisedBottomOpen = false, standingInsturtionOpen = false,
            getLoanBottom = false,
            nominationBottom = false,
            AccountBottom = false,gurdiansaddressselcted_ofthirdnominee=false;
    TextView closeAdditonalDocs, loan_validation_error_txt, loan_validation_error_txt_tenure, closeAddNominee, closeAddNominees, submit_btn_nomine_detail;
    JSONObject AdditionalObj;
    ImageLoader imageLoader;
    String toggleValue = "";
    TextView changeAdditionalDetail;
    TextView nominee;
    WebView reviewWeb;
    Boolean isAadharLinkedOrNot = false;
    String isAadharLinked = "0";
    Button eSignComplete, physicalUpload;
    ScrollView mainScrollview, mainScrollview_new;
    TextView discountMenu, premiumMenu, addressProof;
    String nominee_yes_or_no="";
    EditText discountstockdelivery, discountstockintraday, discountderivativesfutures,
            discountderivativesoptions, discountcurrencyderivativesfutures, discountcurrencyderivativesoptions,
            discountcommodityderivativesfutures, discountcommodityderivativesoptions,
            addone,addtwo,addthree,addpincode,dobofnominee1,dobofnominee2,dobofnominee3;

    EditText edt_gurdiannameone,edt_gurdianaddress_one,edt_gurdianaddress_two,edt_gurdianaddress_three,editgurdianpincode;
    TextView txt_gurdian_city,txt_gurdian_state;

    EditText edt_name_of_nominee2,edtpercentagesharing2,addres_one_of_nominee2,addres_two_of_nominee2,addres_three_of_nominee2,addpincode2
            ,edt_gurdianname2,edt_gurdianaddress2,edt_gurdianaddress_two2,edt_gurdianaddress_three2,edittextpincode2;

    TextView relationshipapplicant2,addstate2,addcity2,percentagesharingofnomineeone2,txt_gurdian_city2,txt_gurdian_state2,addcountry2;
    CheckBox checkBoxaddress_2;

    EditText edt_nameofnominee3,edtpercentagesharing3,edtothername3,addone3,addtwo3,addthree3,addpincode3,
            edt_gurdianname3,edt_gurdianaddress_one3,edt_gurdianaddress_two3,edt_gurdianaddress_three3,edittextpincode3;
    TextView addstate3,addcity3,txt_gurdian_city3,txt_gurdian_state3,addcountry3,relationshipapplicant3;
    CheckBox checkBoxaddress3,checkBoxgurdianaddress3;
    SwitchCompat minorToggle_news3;
    File Nominee_First,Nominee_Second,Nominee_Third;
    File no_imagefile;
    File Guardian_First,Guardian_Second,Guardian_Third;
    boolean isnomineereadable1,isnomineereadable2,isnomineereadable3;

    boolean checkcount=true;
    ImageView cancelnomineelayout3,cancelnomineelayout2,cancelnomineelayout1;
    LinearLayout discountBrokerage, premiumBrokerage, nominee_layoutone, nominee_layouttwo, nominee_layoutthree;
    LinearLayout viewBrockrageReview, identificationproof,gurdianidentificationdocument,Second_gurdianidentificationdocument,identificationproof_of_secondnomine,
            identificationproof_of_thirdnomine, pancardlinearlayout,gurdianidentificationdocument_thirdnominee;
    TextView EdtAdditionText, addressEdit, addressText, userPANText, userBankText, userSelfieText,
            userVarificationText, userSignatureText, userIncomeText,
            userEditIncomeText, addcity, addstate, addcountry, relationshipapplicant,relationshipapplicant_guardian,
            relationshipapplicant_guardian2,relationshipapplicant_guardian3;
    String pdfCompressionTOKEN;
    String server;
    String task;
    boolean isminornominee=false,is_Second_minornominee=false,is_Third_minornominee=false;
    private String dialogEmailText;
    private Dialog dialogEmail;
    private TextView tvEnterEmail;
    private EditText email;
    private RelativeLayout lSumbot;
    boolean isAllFieldsChecked = false;
    RequestQueue requestQueue;
    boolean isAllFieldsChecked_of_secondNominee=false,secondnominegurdiancheck=false;
    boolean isAllFieldsChecked_of_thirdNominee=false,thirdnominegurdiancheck=false;
    String times;
    String inputFormat ;
    String OutPutFormat ;
    String convertedDate;
    String is_nominee="";
    String AddressSameAsAccountHolder="";
    String GuardianAddressSameAsAccountHolder="";
    boolean isGurdianValidation=false;
    private boolean IsEmailExisting = false;
    public static String[] cityArray;
    String formate1="";
    String formate2="";
    String convertedDate1 = "";
    TextWatcher tw ;

    private Calendar cal = Calendar.getInstance();
    boolean exist_nomineimage1=false,exist_nomineimage2=false,exist_nomineimage3=false,exist_gurdianimage1=false,
            exist_gurdianimage2=false,exist_gurdianimage3=false;
    public static FragmentActivity activity_frag;
    LinearLayout gurdian_address1,gurdian_address2,gurdian_address3;
    boolean nomineeImage_first=false,nomineeGurdianImage_first=false,nomineeGurdianImage_second=false,nomineeGurdianImage_third=false,nomineeImage_second=false,nomineeImage_third=false;
    public static String selectedStateName = "";
    ImageView nominee_oneImageview,gurdian_imageview,gurdian_imageviewthird,gurdian_Second_imageview,second_nominee_image,Third_nominee_image;
    String[] selectrelationlist = {"Father", "Mother", "Wife", "Son", "Daughter", "Brother", "Sister", "other"};
    String DOB1,DOB2,DOB3,GurdianDOB1,GurdianDOB2,GurdianDOB3;
    String[] selectrelationlistof_document = {"Aadhar Card", "PAN", "Bank Account", "Other"};
    @Override
    public void onStop() {
        super.onStop();
        user_SegmentsAndPlans.isBackPressed = true;
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.userPanCardText:
                    // Code for button 1 click

                    MainHomeActivity.moveToSectionEdit(0, 1);
                    break;

                case R.id.userDOBText:
                    // Code for button 2 click

                    MainHomeActivity.moveToSectionEdit(0, 1);
                    break;

                case R.id.userNameText:
                    // Code for button 3 click

                    MainHomeActivity.moveToSectionEdit(1, 0);

                    break;

                case R.id.userFNameText:
                    // Code for button 3 click

                    MainHomeActivity.moveToSectionEdit(1, 0);

                    break;

                case R.id.userClientCode_pencil:

                    //Code for button 3 click
                    userClientCode_edt.setVisibility(View.GONE);
                    userClientCode_pencil.setVisibility(View.GONE);
                    userClientCode_Submit.setVisibility(View.VISIBLE);
                    userClientCode_edtitext.setVisibility(View.VISIBLE);
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(PrefixDigit);
                    userClientCode_edtitext.setFilters(fArray);
                    userClientCode_edtitext.setText(userClientCode_edt.getText().toString());
                    userClientCode_edtitext.requestFocus();
                    break;

                case R.id.userClientCode_Submit:
                    // Code for button 3 click

                    savecliencode();
                    break;
                case R.id.userMNameText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 0);
                    break;
                case R.id.userAddressText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 1);

                    break;
                case R.id.userMaritalStatusText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 0);

                    break;
                case R.id.userGenderText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 0);

                    break;
                case R.id.userCityText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 1);

                    break;
                case R.id.userStateText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 1);

                    break;
                case R.id.userPincodeText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(1, 1);

                    break;
                case R.id.userIncomeText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(2, 0);

                    break;
                case R.id.userOccupationText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(2, 0);

                    break;
                case R.id.userPoliticalText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(2, 0);

                    break;
                case R.id.userTradingText:
                    // Code for button 3 click
                    MainHomeActivity.moveToSectionEdit(2, 0);

                    break;

                case R.id.userSegmentText:
                    MainHomeActivity.moveToSectionEdit(3, 0);
                    break;

                case R.id.usermtfText:
                    MainHomeActivity.moveToSectionEdit(3, 0);
                    break;

                case R.id.userDematText:
                    MainHomeActivity.moveToSectionEdit(3, 0);
                    break;

                case R.id.EdtAdditionText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;

                case R.id.addressText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;
                case R.id.userPANText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;
                case R.id.addressEdit:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;

                case R.id.userBankText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;

                case R.id.userIncomeeeText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;
                case R.id.userSelfieText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;
                case R.id.userSignatureText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;
                case R.id.userVarificationText:
                    MainHomeActivity.moveToSectionEdit(4, 0);
                    break;
            }}};
    private BroadcastReceiver ReceivefromService = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String Data = intent.getStringExtra("data");//
            String Method = intent.getStringExtra("method");
            System.out.println("Data>>>" + Data + "");
            System.out.println("Method" + Method + "");
            //{"status":"success","code":200,"message":"Data saved.","data":{"userid":2,"username":"dheeraj","password":"123456","emailid":"dheeraj@gmail.com","dob":"16\/05\/1989","mobilenumber":"dheeraj@gmail.com"}}
            if (Method.equals("EkycReviewDetails")) {
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    System.out.println("ReviewFragment>>>" + Data + "");
                    System.out.println("ReviewFragment : " + " mainObj :  " + mainObj);
                    System.out.println("ReviewFragment" + Method + "");
                    String satus = mainObj.getString("status");
                    if (satus.equals("success")) {
                        /*ShowMessage.showSuccessMessage(mainObj.getString("message"),
                                                                getActivity());*/

                        JSONObject dataObj = mainObj.getJSONObject("data");
                        AppInfo.CUSTOMERID = dataObj.getString("customerId");
                        AppInfo.KRA_FatherName = dataObj.getString("KRA_FatherName");
                        eMudhraSignature = dataObj.getBoolean("eMudhraSignature");
                        eMudhraReferenceNumber = dataObj.getString("eMudhraReferenceNumber");
                        eMudhraAccountStatus = dataObj.getString("eMudhraAccountStatus");
                        AppInfo.IsKRA = dataObj.getBoolean("IsKRA");
                        Log.d("ReviewFragment", eMudhraSignature + "---" + "\n"
                                + eMudhraReferenceNumber + "------" + "\n" + eMudhraAccountStatus);


                        //userAadharFrontImage = (ImageView) getActivity().findViewById(R.id.userFrontImage);
                        stateId = dataObj.getString("StatusId");
                        eSign = dataObj.getBoolean("eSigned");
                        userTradingEditText.setText(dataObj.getString("tradingYears"));
                        userPanEditText.setText(dataObj.getString("panNumber"));


                        customerName = dataObj.getString("firstname") + " " + dataObj.getString("middlename") + " " + dataObj.getString("lastname");
                        newCustomerFullName = dataObj.getString("CustomerFullName");
                        AppInfo.CustomerName = newCustomerFullName;
                        Log.d("EkycReviewDetails", newCustomerFullName + "====");
                        customerFirstName = dataObj.getString("firstname");

                        if (dataObj.has("IsEditableClientCode")) {
                            IsEditableClientCode = dataObj.getString("IsEditableClientCode");
                            System.out.println("ReviewFragment : " + "IsEditableClientCode" + IsEditableClientCode + "..");
                        }
                        if (dataObj.has("UserPrefix")) {
                            UserPrefix = dataObj.getString("UserPrefix");
                            System.out.println("ReviewFragment : " + "UserPrefix" + UserPrefix + "..");
                        }

                        if (dataObj.has("PrefixDigit")) {
                            PrefixDigit = dataObj.getInt("PrefixDigit");
                            System.out.println("ReviewFragment : " + "PrefixDigit" + PrefixDigit + "..");
                        }

                        AppInfo.IsMobileVerify = dataObj.getBoolean("IsMobileVerified");
                        AppInfo.IsEmailVerify = dataObj.getBoolean("IsEmailVerified");
                        AppInfo.IsDigiLockerStatus = dataObj.getBoolean("IsDigiLockerStatus");

                        Log.d("IsMobileVerify", AppInfo.IsMobileVerify + "..");
                        Log.d("IsEmailVerify", AppInfo.IsEmailVerify + "..");
                        Log.d("IsDigiLockerStatus", AppInfo.IsDigiLockerStatus + "..");

                        String aadhar = dataObj.optString("aadharNumber");
                        String aadhar1 = "", aadhar2 = "", aadhar3 = "";

                        if (aadhar.length() == 12) {
                            aadhar1 = aadhar.substring(0, 4);
                            aadhar2 = aadhar.substring(4, 8);
                            aadhar3 = aadhar.substring(8, 12);
                        }

                        String dobStr = dataObj.optString("dob");
                        Log.d("azesolkoll", dobStr.toString() + "------");
                        String dobDD = "", dobMM = "", dobYY = "";
                        if (dobStr.length() >= 8) {
                            dobYY = dobStr.split("-")[2];
                            dobMM = dobStr.split("-")[1];
                            dobDD = dobStr.split("-")[0];
                        }
                        UserIdentity userIdnty = new UserIdentity(
                                dataObj.optString("mobile"),
                                dataObj.optString("email"),
                                dataObj.optString("email"),
                                dataObj.optString("panNumber").replace("null", ""),
                                dobDD,
                                dobMM,
                                dobYY,
                                aadhar1,
                                aadhar2,
                                aadhar3,
                                dataObj.optString("mobile"));
                        FinalApplicationForm.setUserIdentyObject(userIdnty);


                        if (AppInfo.IsKRA) {
                            System.out.println("ReviewFragment : =" + dataObj.getString("fathername") + " equals " + AppInfo.KRA_FatherName + "..");
                            if(AppInfo.KRA_FatherName.equals(dataObj.getString("fathername"))) {
                                pancardlinearlayout.setVisibility(View.GONE);
                            }else {
                                pancardlinearlayout.setVisibility(View.VISIBLE);
                            }
                        }


                        if (customerName == null || customerName.equals("")) {
                            userNameEditText.setText("  ");
                        } else {
                            userNameEditText.setText(customerName);
                        }

                        if (dataObj.getString("fathername").equals("") || dataObj.getString("fathername") == null) {

                            userFNameEditText.setText("  ");

                        } else {

                            userFNameEditText.setText(dataObj.getString("fathername"));
                        }

                        if (dataObj.getString("mothername").equals("") || dataObj.getString("mothername") == null) {

                            userMNameEditText.setText("  ");

                        } else {

                            userMNameEditText.setText(dataObj.getString("mothername"));
                        }

                        if (dataObj.getString("email").equals("") || dataObj.getString("email") == null) {
                            userEmailEdit.setText("  ");
                        } else {
                            userEmailEdit.setText(dataObj.getString("email"));
                        }

                        if (dataObj.getString("mobile").equals("") || dataObj.getString("mobile") == null) {
                            userMobileEdit.setText("  ");
                        } else {
                            userMobileEdit.setText(dataObj.getString("mobile"));
                        }

                        if (dataObj.getString("email").equals("") || dataObj.getString("email") == null) {
                            MainEmail = dataObj.getString("  ");
                        } else {
                            MainEmail = dataObj.getString("email");
                        }

                        if (dataObj.getString("email").equals("") || dataObj.getString("email") == null) {
                            dialogEmailText = dataObj.getString("  ");
                        } else {
                            dialogEmailText = dataObj.getString("email");
                        }


                        Log.d("ReviewFragment", dialogEmailText + "---");
                        AppInfo.ClientCode = dataObj.optString("ClientCode");

                        if(dataObj.getString("gender").equals("1")) {
                            userGenderEditText.setText("MALE");
                        }else if (dataObj.getString("gender").equals("2")) {
                            userGenderEditText.setText("FEMALE");
                        } else if (dataObj.getString("gender").equals("3")) {
                            userGenderEditText.setText("OTHER");
                        }

                        if (dataObj.getString("gender") == null || dataObj.getString("gender").equals("")) {
                            userGenderEditText.setText(" ");
                        } else {
                            userGenderEditText.setText(dataObj.getString("gender"));
                        }

                        if (dataObj.getString("maritalstatus") == null || dataObj.getString("maritalstatus").equals("")) {
                            userMaritalStatusEditText.setText(" ");
                        } else {
                            userMaritalStatusEditText.setText(dataObj.getString("maritalstatus"));
                        }


                        if (dataObj.getString("tradingYears") == null || dataObj.getString("tradingYears").equals("")) {
                            userTradingEditText.setText(" ");
                        } else {
                            userTradingEditText.setText(dataObj.getString("tradingYears"));
                        }


                        if (dataObj.getString("dob") != null) {

                            String[] separated = dataObj.getString("dob").split("T");
                            String one = separated[0];
                            userDOBEditText.setText(one);
                        } else {
                            userDOBEditText.setText("   ");
                        }


                        userAddressEditText.setText(dataObj.getString("address1") + " " + dataObj.getString("address2") + " " + dataObj.getString("address3"));
                        userCityEditText.setText("");
                        userStateEditText.setText("");

                        if (dataObj.getString("pincode") == null ||
                                dataObj.getString("pincode").equals("")) {
                            userPostcalCodeEditText.setText(" ");
                        } else {
                            userPostcalCodeEditText.setText(dataObj.getString("pincode"));
                        }

                        if (IsEditableClientCode.equals("Yes")) {
                            client_code_editable_section.setVisibility(View.VISIBLE);
                            client_code_section.setVisibility(View.GONE);

                            userClientCode_edt_lable.setText(UserPrefix);

                            userClientCode_edtitext.setVisibility(View.GONE);
                            userClientCode_Submit.setVisibility(View.GONE);

                            if (AppInfo.ClientCode == null ||
                                    AppInfo.ClientCode.equals("")) {
                                userClientCode_edt.setText(" ");
                            } else {
                                int prefix_length = UserPrefix.length();
                                int clientcode_length = AppInfo.ClientCode.length();

                                System.out.println("ReviewFragment : " + "prefix_length" + prefix_length + "..");
                                System.out.println("ReviewFragment : " + "clientcode_length" + clientcode_length + "..");

                                final_edt_clientcode = AppInfo.ClientCode.substring(prefix_length, clientcode_length);
                                userClientCode_edt.setVisibility(View.VISIBLE);
                                userClientCode_edt.setText(final_edt_clientcode);
                                userClientCode_pencil.setVisibility(View.VISIBLE);

                            }
                        } else {
                            client_code_editable_section.setVisibility(View.GONE);
                            client_code_section.setVisibility(View.VISIBLE);

                            if (AppInfo.ClientCode == null ||
                                    AppInfo.ClientCode.equals("")) {
                                userClientCode.setText(" ");
                            } else {
                                userClientCode.setText(AppInfo.ClientCode);
                            }
                        }

                        if (dataObj.getString("city") == null ||
                                dataObj.getString("city").equals("")) {
                            userCityEditText.setText(" ");
                        } else {
                            userCityEditText.setText(dataObj.getString("city"));
                        }

                        if (dataObj.getString("statename") == null ||
                                dataObj.getString("statename").equals("")) {
                            userStateEditText.setText(" ");
                        }else {
                            userStateEditText.setText(dataObj.getString("statename"));
                        }
                        userCityEditText.setEnabled(false);
                        userStateEditText.setEnabled(false);
                        if (dataObj.getString("income") == null ||
                                dataObj.getString("income").equals("")) {
                            userIncomeEditText.setText(" ");
                        } else {
                            userIncomeEditText.setText(dataObj.getString("income"));
                        }

                        if (dataObj.getString("occupation") == null ||
                                dataObj.getString("occupation").equals("")) {
                            userOccupationEditText.setText(" ");
                        } else {
                            userOccupationEditText.setText(dataObj.getString("occupation"));
                        }


                        AppInfo.IsEquityAndCurrencyDerivatives = dataObj.getBoolean("IsEquityAndCurrencyDerivatives");
                        AppInfo.IsStocksAndMutualFunds = dataObj.getBoolean("IsStocksAndMutualFunds");
                        AppInfo.IsCommodityDerivatives = dataObj.getBoolean("IsCommodityDerivatives");
                        AppInfo.IsEditableClientCode = dataObj.getString("IsEditableClientCode");
                        AppInfo.IsLoanApplied = dataObj.getString("IsLoanApplied");

                        String finalmtfstring = "";
                        if ((dataObj.optString("politicallyAffiliated")).equalsIgnoreCase("1")) {

                            userPolicalPartyEditText.setText("YES");

                        } else {

                            userPolicalPartyEditText.setText("NO");
                        }

                        esigned = dataObj.getBoolean("eSigned");
                        MICR = dataObj.optString("MICR");
                        BankBranch = dataObj.optString("BankBranch");
                        BankCity = dataObj.optString("BankCity");
                        BankState = dataObj.optString("BankState");
                        BankZipCode = dataObj.optString("BankZipCode");
                        country = dataObj.optString("BankCountry");

                        AccountNumber = dataObj.optString("AccountNumber");
                        AccountHolderName = dataObj.optString("AccountHolderName");
                        IfscCode = dataObj.optString("IfscCode");
                        BankName = dataObj.optString("BankName");
                        currentstatus = dataObj.optString("status");

                        System.out.println("ReviewFragment : " + "in getcustomerbyURL : currentstatus " + currentstatus);

                        JSONArray segmentNameArray = new JSONArray(dataObj.getString("customersegment"));
                        String segmentsNameList = "";
                        for (int i = 0; i < segmentNameArray.length(); i++) {
                            JSONObject ob = segmentNameArray.getJSONObject(i);


                            if (ob.getString("segmentName").equals("Commodity Derivatives")) {

                                isCommoditySelected = true;
                            }
                            if (segmentsNameList.equals("")) {
                                segmentsNameList = ob.getString("segmentName");
                            } else {
                                segmentsNameList = segmentsNameList + "," + ob.getString("segmentName");
                            }

                        }
                        System.out.println("detailErr>>>" + segmentsNameList);
                        userSegmentsEditText.setText(segmentsNameList);
                        System.out.println("ReviewFragment : " + "finish sucess ekycreviewdetails: ");


                    } else {

                        ShowMessage.showErrorMessage(mainObj.getString("message"),
                                getActivity());

                    }

                }catch (JSONException ex) {
                    System.out.println("ReviewFragment" + "detailErr" + ex.getMessage());
                }
                System.out.println("ReviewFragment : " + "finish ekycreviewdetails: ");
                GetDocumentsByEkycId(getActivity());

            } else if (Method.equals("GetDocumentsByEkycId")) {
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    System.out.println("GetDocumentsByEkycId" + mainObj.toString());
                    String satus = mainObj.getString("status");
                    if (satus.equals("success")) {
                        JSONObject dataObj = mainObj.getJSONObject("data");
                        AppInfo.ekycApplicationId = dataObj.getString("EkycId");
                        JSONArray documents = dataObj.getJSONArray("Documents");
                        System.out.println("DocumentArrayInReview" + dataObj.toString());
                        saveDocumentsData(documents,
                                esigned,
                                MICR,
                                BankBranch,
                                BankCity,
                                BankState,
                                BankZipCode,
                                country,
                                AccountNumber,
                                AccountHolderName,
                                IfscCode,
                                BankName);

                        for (int i = 0; i < documents.length(); i++) {
                            JSONObject docObj = documents.getJSONObject(i);
                            try {
                                if(docObj.getString("documentType").equals("Aadhar_Back")) {
                                    ImgStrAadharBack = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                            .setTag("aadharBack")
                                            .setPriority(Priority.MEDIUM)
                                            .setBitmapMaxHeight(100)
                                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                            .setBitmapMaxWidth(100)
                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                            .build()
                                            .getAsBitmap(new BitmapRequestListener() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    userAadharBackImage.setImageBitmap(bitmap);
                                                }
                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                }
                                            });
                                    /*Picasso.get().
                                            load("http://" + docObj.getString("documentUrl")).
                                            resize(120, 60).
                                            into(userAadharBackImage);*/


                                } else if (docObj.getString("documentType").equals("AdditionalDocument1")) {
                                    if(!docObj.getString("documentUrl").contains(".pdf")) {
                                        ImgStrAdditional1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("AdditionalDocument1")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        //do anything with bitmap
                                                        additionalImageone.setImageBitmap(bitmap);
                                                        llSuporttivedoc.setVisibility(View.VISIBLE);
                                                    }
                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error
                                                        llSuporttivedoc.setVisibility(View.GONE);
                                                    }
                                                });
                                    } else {

                                        AddtioinalOnePDFurl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        ImgStrAdditional1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        System.out.println("AddtioinalOnePDFurl" + ">>>" + AddtioinalOnePDFurl);
                                        additionalImageone.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                        llSuporttivedoc.setVisibility(View.VISIBLE);
                                    }

                                }else if (docObj.getString("documentType").equals("AdditionalDocument2")) {
                                    if(!docObj.getString("documentUrl").contains(".pdf")) {
                                        ImgStrAdditional2 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("AdditionalDocument2")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        // do anything with bitmap
                                                        additionalImagetwo.setImageBitmap(bitmap);
                                                        llSuporttivedoc.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error
                                                        llSuporttivedoc.setVisibility(View.GONE);
                                                    }
                                                });
                                    } else {
                                        AddtioinalTwoPDFurl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        ImgStrAdditional2 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
//                                    additionalImageone.setImageBitmap(bitmap);
                                        additionalImagetwo.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                        llSuporttivedoc.setVisibility(View.VISIBLE);
                                    }

                                } else if (docObj.getString("documentType").equals("OtherAddressProof")) {


                                    AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                            .setTag("OtherAddressProof")
                                            .setPriority(Priority.MEDIUM)
                                            .setBitmapMaxHeight(100)
                                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                            .setBitmapMaxWidth(100)
                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                            .build()
                                            .getAsBitmap(new BitmapRequestListener() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    // do anything with bitmap
                                                    addressProofImage.setImageBitmap(bitmap);

                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error

                                                }
                                            });


                                } else if (docObj.getString("documentType").equals("VerificationVideo")) {
                                    System.out.println("ReviewFragment : " + "documenttype : " + docObj.getString("documentType"));
                                    ImgStrVarification = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    VideoUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    System.out.println("ReviewFragment : " + "VideoUrl : ImgStrVarification" + ImgStrVarification);


                                    VideoExtension = docObj.getString("Extension");
                                    System.out.println("ReviewFragment : " + "VideoExtension : ImgStrVarification : " + VideoExtension);

                                    if (VideoUrl.equals("")) {
                                        llVarification.setVisibility(View.GONE);
                                    } else {
                                        llVarification.setVisibility(View.VISIBLE);
                                    }

                                } else if (docObj.getString("documentType").equals("Aadhar_Front")) {
                                    ImgStrAadharFront = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                                    AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                            .setTag("Aadhar_Front")
                                            .setPriority(Priority.MEDIUM)
                                            .setBitmapMaxHeight(100)
                                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                            .setBitmapMaxWidth(100)
                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                            .build()
                                            .getAsBitmap(new BitmapRequestListener() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    // do anything with bitmap
                                                    userAadharFrontImage.setImageBitmap(bitmap);

                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                }
                                            });

                                } else if (docObj.getString("documentType").equals("Bank_Statement")) {
                                    ImgStrBank1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    if (!docObj.getString("documentUrl").contains(".pdf")) {
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("Bank_Statement")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        // do anything with bitmap
                                                        userBankImage.setImageBitmap(bitmap);

                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error  Salary_Slip
                                                    }
                                                });

                                    } else {

                                        ImgStrBank1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        BankstatemaneOne = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        userBankImage.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));

                                    }
                                } else if (docObj.getString("documentType").equals("Salary_Slip")) {
                                    if (!docObj.getString("documentUrl").contains(".pdf")) {
                                        ImgStrBank1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("Bank_Statement")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        // do anything with bitmap
                                                        userBankImage.setImageBitmap(bitmap);

                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error  Salary_Slip
                                                    }
                                                });
                                    } else {

                                        BankstatemaneOne = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        ImgStrBank1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        userBankImage.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                    }

                                }else if (docObj.getString("documentType").equals("ITR")) {
                                    if (!docObj.getString("documentUrl").contains(".pdf")) {
                                        ImgStrBank1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("ITR")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        // do anything with bitmap
                                                        userBankImage.setImageBitmap(bitmap);

                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error
                                                    }
                                                });

                                    } else {
                                        BankstatemaneOne = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        ImgStrBank1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                                        userBankImage.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));

                                    }
                                } else if (docObj.getString("documentType").equals("Cancelled_Cheque")) {
                                    if (!docObj.getString("documentUrl").contains(".pdf")) {
                                        ImgStrBan2 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("ITR")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setBitmapMaxWidth(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {

                                                        if (bitmap == null) {
                                                            userBankImage2.setVisibility(View.GONE);
                                                        } else {
                                                            // do anything with bitmap
                                                            userBankImage2.setImageBitmap(bitmap);
                                                        }


                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error
                                                    }
                                                });
                                    } else {
                                        userBankImage2.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                        BankstatemaneTwo = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                        ImgStrBan2 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                                    }

                                } else if (docObj.getString("documentType").equals("Photo")) {

                                    ImgStrPhoto = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                            .setTag("Photo")
                                            .setPriority(Priority.MEDIUM)
                                            .setBitmapMaxHeight(100)
                                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                            .setBitmapMaxWidth(100)
                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                            .build()
                                            .getAsBitmap(new BitmapRequestListener() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    // do anything with bitmap
                                                    userPhotoImage.setImageBitmap(bitmap);

                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                }
                                            });


                                } else if (docObj.getString("documentType").equals("Signature")) {

                                    ImgStrSignature = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                                    AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                            .setTag("Signature")
                                            .setPriority(Priority.MEDIUM)
                                            .setBitmapMaxHeight(100)
                                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                            .setBitmapMaxWidth(100)
                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                            .build()
                                            .getAsBitmap(new BitmapRequestListener() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    // do anything with bitmap
                                                    userSignatureImage.setImageBitmap(bitmap);

                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                }
                                            });


                                } else if (docObj.getString("documentType").equals("Pan")) {
                                    ImgStrPAN = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                                    AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                            .setTag("Pan")
                                            .setPriority(Priority.MEDIUM)
                                            .setBitmapMaxHeight(100)
                                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                            .setBitmapMaxWidth(100)
                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                            .build()
                                            .getAsBitmap(new BitmapRequestListener() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    // do anything with bitmap
                                                    userPanImage.setImageBitmap(bitmap);

                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                }
                                            });

                                } else if (docObj.getString("documentType").equals("Income")) {

                                    ImgStrIncome = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    if (ImgStrIncome.equals("")) {
                                        incomeDocLayout.setVisibility(View.GONE);
                                    } else {
                                        incomeDocLayout.setVisibility(View.VISIBLE);
                                    }
                                    if (!docObj.getString("documentUrl").contains(".pdf")) {
                                        AndroidNetworking.get(docObj.getString("documentBaseUrl") + docObj.getString("documentUrl"))
                                                .setTag("Income")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        // do anything with bitmap
                                                        userIncomeImage.setImageBitmap(bitmap);

                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error
                                                    }
                                                });
                                    } else {
                                        userIncomeImage.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                        incomePDF = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                                    }

                                }


                                if (!currentstatus.equals("")) {
                                    if (currentstatus.equals("PendingEsign")) {

                                        ll_reviewSubmit.setVisibility(View.VISIBLE);

                                    } else if (currentstatus.equals("Pending Authorization")) {

                                        ll_reviewSubmit.setVisibility(View.GONE);
                                        EdtAdditionText.setVisibility(View.INVISIBLE);
                                        userBankText.setVisibility(View.INVISIBLE);

                                    } else if (currentstatus.equals("Authorized")) {

                                        ll_reviewSubmit.setVisibility(View.GONE);
                                        EdtAdditionText.setVisibility(View.INVISIBLE);
                                        userBankText.setVisibility(View.INVISIBLE);

                                    } else if (currentstatus.equals("In Progress")) {

                                        ll_reviewSubmit.setVisibility(View.VISIBLE);
                                    } else if (currentstatus.equals("Review")) {
                                        ll_reviewSubmit.setVisibility(View.GONE);
                                        EdtAdditionText.setVisibility(View.INVISIBLE);
                                        userBankText.setVisibility(View.INVISIBLE);
                                    } else if (currentstatus.equals("ReadyToDispatch")) {
                                        ll_reviewSubmit.setVisibility(View.VISIBLE);
                                    } else if (currentstatus.equals("Rejected")) {
                                        ll_reviewSubmit.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_reviewSubmit.setVisibility(View.GONE);
                                        EdtAdditionText.setVisibility(View.INVISIBLE);
                                        userBankText.setVisibility(View.INVISIBLE);
                                    }
                                }

                            } catch (Exception ex) {
                                System.out.println("ReviewFragment : " + "Exception" + ex.getMessage() + "..");

                            }
                        }

                        System.out.println("ReviewFragment : " + " GetDocumentsByEkycId : in success finish" + "..");

                    } else {

            /*ShowMessage.showErrorMessage(mainObj.getString("message"),
                                getActivity());*/
                    }


                } catch (JSONException ex) {

                    System.out.println("JSONExceptionDashFrag" + ex.getMessage());
                    System.out.println("GetDocumentsByEkycId " + "JSONExceptionDashFrag" + ex.getMessage());
                }

                System.out.println("ReviewFragment : " + " GetDocumentsByEkycId  : in finish" + "..");


            } else if (Method.equals("GeteSignSourceDetails")) {

                try {

                    System.out.println("GeteSignSourceDetails" + "Method " + Method + "");
                    JSONObject mainObj = new JSONObject(Data);
                    System.out.println("GeteSignSourceDetails" + "mainObj " + mainObj + "");

                    String status = mainObj.getString("status");
                    String message = mainObj.getString("message");


                    System.out.println("GeteSignSourceDetails>>>" + "status" + status + "");
                    System.out.println("GeteSignSourceDetails>>>" + "message" + message + "");

                    if (status.equals("success")) {

                        /*ShowMessage.showSuccessMessage(mainObj.getString("message"),
                                getActivity());*/

                        JSONObject dataObj = mainObj.getJSONObject("data");
                        System.out.println("GeteSignSourceDetails>>>" + "dataObj" + dataObj + "");

                        int CompanyId = dataObj.getInt("CompanyId");
                        String CompanyName = dataObj.getString("CompanyName");
                        String eSignatureWeb = dataObj.getString("eSignatureWeb");
                        String eSignatureMobile = dataObj.getString("eSignatureMobile");
                        String eSignatureMessage = dataObj.getString("eSignatureMessage");
                        boolean IsDigilocker = dataObj.getBoolean("IsDigilocker");
                        String DigilockerMsg = dataObj.getString("DigilockerMsg");

                        System.out.println("GeteSignSourceDetails>>>" + "CompanyId" + CompanyId + "");
                        System.out.println("GeteSignSourceDetails>>>" + "CompanyName" + CompanyName + "");
                        System.out.println("GeteSignSourceDetails>>>" + "eSignatureWeb" + eSignatureWeb + "");
                        System.out.println("GeteSignSourceDetails>>>" + "eSignatureMobile" + eSignatureMobile + "");
                        System.out.println("GeteSignSourceDetails>>>" + "eSignatureMessage" + eSignatureMessage + "");
                        System.out.println("GeteSignSourceDetails>>>" + "IsDigilocker" + IsDigilocker + "");
                        System.out.println("GeteSignSourceDetails>>>" + "DigilockerMsg" + DigilockerMsg + "");


                        if (IsDigilocker == true) {
                            openNewAaadharQuestionDialog(getActivity());
                        } else {

                            //checkPermissionsAndOpenFilePicker_digilocker();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(DigilockerMsg)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                            AppInfo.IsAadharLink = false;
                                            Log.d("IsKraValue", AppInfo.IsKRA + "");
                                            if (AppInfo.IsKRA) {
                                                if (AppInfo.IsEmailVerify) {

                                                    if (stateId.equals("4") && eSign) {
                                                        Callsubmitdata();
                                                    } else {
                                                        if (eSign) {
                                                            // submit to kyc screen
                                                            Intent intent = new Intent(getActivity(), SaveClientCode.class);
                                                            startActivity(intent);
                                                        } else {
                                                            // Esign On Emudra screen
                                                            NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                            NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                            NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                            NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                            NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                            NewEntryFragment.step10Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                                                            tabhost.getTabAt(8).select();
                                                        }
                                                    }

                                                } else {
                                                    // Email Verify Screen
                                                    NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                    NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                    NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                    NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                    NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                                                    tabhost.getTabAt(6).select();
                                                }
                                            } else {
                                                // Address screen
                                                Log.d("IsKraValue", AppInfo.IsKRA + "ELSE");
                                                NewEntryFragment.step4Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                                                tabhost.getTabAt(9).select();
                                            }
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    } else {

                        ShowMessage.showErrorMessage(mainObj.getString("message"),
                                getActivity());

                    }

                } catch (JSONException ex) {

                    System.out.println("detailErr" + ex.getMessage());

                }

            } else if(Method.equals("EkycAddressDetails")) {

                ProgressDialog progressdialog = new ProgressDialog(getActivity());
                progressdialog.setMessage("Please Wait....");
                progressdialog.show();
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    Log.e("REVIEW_AMAN>>>", Data + "");
                    Log.e("Method_aman", Method + "");
                    String satus = mainObj.getString("status");
                    if(satus.equals("success")) {
                        progressdialog.dismiss();
                        JSONObject dataObj = mainObj.getJSONObject("data");
                        AppInfo.CUSTOMERID = dataObj.getString("customerId");
                        AppInfo.ekycApplicationId = dataObj.getString("ekycApplicationId");
                        AppInfo.ClientCode = dataObj.getString("ClientCode");
                        AppInfo.IsMobileVerify = dataObj.getBoolean("IsMobileVerified");
                        AppInfo.IsEmailVerify = dataObj.getBoolean("IsEmailVerified");

                        addpincode.setText(dataObj.getString("pincode"));
                        addone.setText(dataObj.getString("address1"));
                        addtwo.setText(dataObj.getString("address2"));
                        addthree.setText(dataObj.getString("address3"));
                        addstate.setText(dataObj.getString("statename"));
                        addcity.setText(dataObj.getString("city"));
                        if(gurdiansaddressselcted){
                            editgurdianpincode.setText(dataObj.getString("pincode"));
                            edt_gurdianaddress_one.setText(dataObj.getString("address1"));
                            edt_gurdianaddress_two.setText(dataObj.getString("address2"));
                            edt_gurdianaddress_three.setText(dataObj.getString("address3"));
                            txt_gurdian_city.setText(dataObj.getString("city"));
                            txt_gurdian_state.setText(dataObj.getString("statename"));

                        }else {
                            editgurdianpincode.setText("");
                            edt_gurdianaddress_one.setText("");
                            edt_gurdianaddress_two.setText("");
                            edt_gurdianaddress_three.setText("");
                            txt_gurdian_city.setText("");
                            txt_gurdian_state.setText("");
                        }

                        if(third_Nominee_Address){
                            //Toast.makeText(getActivity(), "Pincode="+dataObj.getString("pincode"), Toast.LENGTH_LONG).show();
                            addpincode3.setText(dataObj.getString("pincode"));
                            addone3.setText(dataObj.getString("address1"));
                            addtwo3.setText(dataObj.getString("address2"));
                            addthree3.setText(dataObj.getString("address3"));
                            addcity3.setText(dataObj.getString("city"));
                            addstate3.setText(dataObj.getString("statename"));
                        }else{
                            addpincode3.setText("");
                            addone3.setText("");
                            addtwo3.setText("");
                            addthree3.setText("");
                            addcity3.setText("");
                            addstate3.setText("");
                        }

                        if(gurdiansaddressselcted_ofsecondnominee){
                            edittextpincode2.setText(dataObj.getString("pincode"));
                            edt_gurdianaddress2.setText(dataObj.getString("address1"));
                            edt_gurdianaddress_two2.setText(dataObj.getString("address2"));
                            edt_gurdianaddress_three2.setText(dataObj.getString("address3"));
                            txt_gurdian_city2.setText(dataObj.getString("city"));
                            txt_gurdian_state2.setText(dataObj.getString("statename"));

                        }else {
                            edittextpincode2.setText("");
                            edt_gurdianaddress2.setText("");
                            edt_gurdianaddress_two2.setText("");
                            edt_gurdianaddress_three2.setText("");
                            txt_gurdian_city2.setText("");
                            txt_gurdian_state2.setText("");
                        }

                        if(gurdiansaddressselcted_ofthirdnominee){

                            edittextpincode3.setText(dataObj.getString("pincode"));
                            edt_gurdianaddress_one3.setText(dataObj.getString("address1"));
                            edt_gurdianaddress_two3.setText(dataObj.getString("address2"));
                            edt_gurdianaddress_three3.setText(dataObj.getString("address3"));
                            txt_gurdian_city3.setText(dataObj.getString("city"));
                            txt_gurdian_state3.setText(dataObj.getString("statename"));
                        }else{
                            edittextpincode3.setText("");
                            edt_gurdianaddress_one3.setText("");
                            edt_gurdianaddress_two3.setText("");
                            edt_gurdianaddress_three3.setText("");
                            txt_gurdian_city3.setText("");
                            txt_gurdian_state3.setText("");
                        }

                        if(secondn_Nominee_Address){
                            addpincode2.setText(dataObj.getString("pincode"));
                            addres_one_of_nominee2.setText(dataObj.getString("address1"));
                            addres_two_of_nominee2.setText(dataObj.getString("address2"));
                            addres_three_of_nominee2.setText(dataObj.getString("address3"));
                            addcity2.setText(dataObj.getString("city"));
                            addstate2.setText(dataObj.getString("statename"));

                        }else{
                            addpincode2.setText("");
                            addres_one_of_nominee2.setText("");
                            addres_two_of_nominee2.setText("");
                            addres_three_of_nominee2.setText("");
                            addcity2.setText("");
                            addstate2.setText("");
                        }
                    }else{
                        addpincode.setText("");
                        addone.setText("");
                        addtwo.setText("");
                        addthree.setText("");
                        addcity.setText("");
                        addstate.setText("");
                    }
                }catch (JSONException ex) {
                    Log.e("detailErr", ex.getMessage());
                    System.out.println("EkycAddressDetails : " + " detailErr " + ex.getMessage());
                }
                System.out.println("EkycAddressDetails : " + " in finish ");
                GetDocumentsByEkycId(getActivity());
            }else if(Method.equals("setAaadharLinkedStatus")) {
                try{
                    JSONObject mainObj = new JSONObject(Data);
                    String satus = mainObj.getString("status");
                    if(satus.equals("success")) {
                        isAadharLinkedOrNot = isAadharLinked.equals("1");
                        String xml = "<Esign \n" +
                                "ver\n" +
                                "=\"2.1\" \n" +
                                "sc\n" +
                                "=\"Y\" ts=\"2018-10-31T07:01:25+00:00\" txn=\"TC142562\" \n" +
                                "ekycIdType=\"A\" \n" +
                                "aspId=\"ASPSILMUMTEST214\" \n" +
                                "AuthMode\n" +
                                "=\"1\"\n" +
                                "responseSigType\n" +
                                "=\"rawrsa\"\n" +
                                "responseUrl=\"http://swastika.co.in\"\n" +
                                ">\n" +
                                "<Docs>\n" +
                                "<Input\n" +
                                "Hash  id=\"1\"\n" +
                                "hashAlgorithm=\"SHA256\"\n" +
                                "docInfo=\"\"\n" +
                                ">Document \n" +
                                "Hash in Hex</Input\n" +
                                "Hash\n" +
                                ">\n" +
                                "</Docs>\n" +
                                "<Signature>Digital signature of ASP</Signature>\n" +
                                "</Esign>";


                        if (isAadharLinkedOrNot) {

                           /* Intent appStartIntent = new Intent(getActivity(),
                                    NsdlEsignActivity.class);
                            //MainActivity should be the ASP Activity
                            appStartIntent.putExtra("msg", xml); // msg contains esign request xml from ASP.
                            appStartIntent.putExtra("env", "PREPROD"); //Possible values PREPROD or PROD
                            appStartIntent.putExtra("returnUrl", "http://swastika.co.in"); // your package name where esign
                            startActivityForResult(appStartIntent, REQUEST_CODE);
*/
                        } else {
                            IsBoolean = true;

                            review_applicationMainLayout.setVisibility(View.GONE);
                            error_with_aadhar.setVisibility(View.GONE);
                            congratulationsLayout.setVisibility(View.GONE);
                            aadhar_not_linked.setVisibility(View.VISIBLE);
                            //additional_doc.setVisibility(View.GONE);
                            reviewWebView.setVisibility(View.GONE);
                        }
                    }else{

                        ShowMessage.showErrorMessage(mainObj.getString("message"),
                                activity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(Method.equals("updateLoanDetails")) {
                try{
                    getLoanDetails(AppInfo.ekycApplicationId);
                    JSONObject mainObj = new JSONObject(Data);
                    String satus = mainObj.getString("status");
                    getLoan_d_bottom.setVisibility(View.GONE);
                    if(getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                        submit_btn_additional_detail.setVisibility(View.GONE);
                    }else{
                        submit_btn_additional_detail.setVisibility(View.VISIBLE);
                    }
                    if(satus.equals("success")) {
                    }else{
                        getLoan_d_bottom.setVisibility(View.GONE);
                        if(getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                            submit_btn_additional_detail.setVisibility(View.GONE);
                        }else{
                            submit_btn_additional_detail.setVisibility(View.VISIBLE);
                        }
                        ShowMessage.showErrorMessage(mainObj.getString("message"), getActivity());
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (Method.equals("BrokerageDocument")) {
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    String satus = mainObj.getString("status");
                    brokrafeNotes.setText(brpkrageDescription);
                } catch (JSONException ex) {
                }
            }else if (Method.equals("GetLoanDetails")) {
                try {
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                    getLoan_plus_img.setImageResource(R.drawable.plus);
                    Log.d("GetLoanDetails", "Chla");
                    JSONObject mainObj = new JSONObject(Data).getJSONObject("data");
                    eKycId = mainObj.getString("EkycId");
                    customerId = mainObj.getString("CustomerId");
                    additionalDetailsIdd = mainObj.getString("AdditionalDetailId");
                    if (mainObj.getString("LoanAmount") == "null" || mainObj.getString("LoanAmount").equals("")) {
                        loanAmount = 250000 + "";
                    } else {
                        loanAmount = mainObj.getString("LoanAmount");
                    }
                    loanTenurePeriod = mainObj.getString("LoanTenurePeriod");
                    pdc_udc_number = mainObj.getString("PDC_UDC_Number");
                    chequeNumberFirst = mainObj.getString("ChequeNo_first");
                    bankNumberFirst = mainObj.getString("Bank_first");
                    branchFirst = mainObj.getString("Branch_first");
                    chequeNumberSecond = mainObj.getString("ChequeNo_second");
                    bankNumberSecond = mainObj.getString("Bank_second");
                    branchSecond = mainObj.getString("Branch_second");
                    bankStatementDocUrl = mainObj.getString("Path_GetLoan_Bank_Statement");
                    chequeOneDocUrl = mainObj.getString("Path_GetLoan_Cheque1");
                    chequeTwoDocUrl = mainObj.getString("Path_GetLoan_Cheque2");
                    if(loanAmount != null || !loanAmount.equals("")) {
                        etLoanAmount.setText(loanAmount + "");
                    } else {
                        etLoanAmount.setText("");
                    }
                    if(loanTenurePeriod != null) {
                        etLoanTenurePeriod.setText(loanTenurePeriod + "");
                    }else {
                        etLoanTenurePeriod.setText("");
                    }
                    if(chequeNumberFirst != null) {
                        etChequeFirst.setText(chequeNumberFirst + "");
                    } else {
                        etChequeFirst.setText("");
                    }
                    if(chequeNumberSecond != null) {
                        etchequeSecond.setText(chequeNumberSecond + "");
                    }else {
                        etchequeSecond.setText("");
                    }
                    if(bankNumberFirst.equals("")) {
                        etBankFirst.setText("");
                    } else {
                        etBankFirst.setText(bankNumberFirst + "");
                    }
                    if (bankNumberSecond != null) {
                        etBankSecond.setText(bankNumberSecond + "");
                    } else {
                        etBankSecond.setText("");
                    }

                    if (branchFirst != null) {
                        etBranchFirst.setText(branchFirst + "");
                    } else {
                        etBranchFirst.setText("");
                    }

                    if (branchSecond != null) {
                        etBranchSecond.setText(branchSecond + "");
                    } else {
                        etBranchSecond.setText("");
                    }

                    etBranchSecond.setText(branchSecond + "");


                    if (bankStatementDocUrl.equals("")) {
                        BankBox1Image.setImageDrawable(activity.getResources().getDrawable(R.drawable.upload));

                    } else {
                        if (bankStatementDocUrl != null) {

                            if (!bankStatementDocUrl.contains(".pdf")) {
                                AndroidNetworking.get(bankStatementDocUrl)
                                        .setTag("aadharFront")
                                        .setPriority(Priority.MEDIUM)
                                        .setOkHttpClient(UtilsClass.getSlrClubClient1(getActivity()))
                                        .setBitmapMaxHeight(BankBox1Image.getHeight() * 2)
                                        .setBitmapMaxWidth(BankBox1Image.getWidth() * 2)
                                        .doNotCacheResponse()
                                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                        .build()
                                        .getAsBitmap(new BitmapRequestListener() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                isbankStatementUploaded = true;
                                                BankBox1Image.setImageBitmap(bitmap);
                                                getLoanDetailsButtonvalidation();

                                            }

                                            @Override
                                            public void onError(ANError error) {

                                                isbankStatementUploaded = false;
                                            }
                                        });
                            } else {

                                isbankStatementUploaded = true;
                                getLoanDetailsButtonvalidation();
                                BankBox1Image.setImageDrawable(activity.getResources().getDrawable(R.mipmap.new_pdf_image));
                                //   BankBox1Image.setImageDrawable(activity.getResources().getDrawable(R.mipmap.new_pdf_image));

                            }

                        }
                    }

                    if (chequeOneDocUrl.equals("")) {
                        cheque1fBox1Image.setImageDrawable(activity.getResources().getDrawable(R.drawable.upload));
                    } else {
                        if (chequeOneDocUrl != null) {
                            if (!chequeOneDocUrl.contains(".pdf")) {
                                AndroidNetworking.get(chequeOneDocUrl)
                                        .setTag("aadharFront")
                                        .setPriority(Priority.MEDIUM)
                                        .setOkHttpClient(UtilsClass.getSlrClubClient1(getActivity()))
                                        .setBitmapMaxHeight(cheque1fBox1Image.getHeight() * 2)
                                        .setBitmapMaxWidth(cheque1fBox1Image.getWidth() * 2)
                                        .doNotCacheResponse()
                                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                        .build()
                                        .getAsBitmap(new BitmapRequestListener() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                isChequeOneUploaded = true;
                                                getLoanDetailsButtonvalidation();
                                                cheque1fBox1Image.setImageBitmap(bitmap);


                                            }

                                            @Override
                                            public void onError(ANError error) {
                                                isChequeOneUploaded = false;
                                            }
                                        });
                            } else {
                                isChequeOneUploaded = true;
                                getLoanDetailsButtonvalidation();
                                cheque1fBox1Image.setImageDrawable(activity.getResources().getDrawable(R.mipmap.new_pdf_image));
                            }

                        }
                    }

                    if (chequeTwoDocUrl.equals("")) {
                        uploadCheque2Box2ImageTwo.setImageDrawable(activity.getResources().getDrawable(R.drawable.upload));

                    } else {
                        if (chequeTwoDocUrl != null) {
                            if (!chequeTwoDocUrl.contains(".pdf")) {
                                AndroidNetworking.get(chequeTwoDocUrl)
                                        .setTag("aadharFront")
                                        .setPriority(Priority.MEDIUM)
                                        .setOkHttpClient(UtilsClass.getSlrClubClient1(getActivity()))
                                        .setBitmapMaxHeight(uploadCheque2Box2ImageTwo.getHeight() * 2)
                                        .setBitmapMaxWidth(uploadCheque2Box2ImageTwo.getWidth() * 2)
                                        .doNotCacheResponse()
                                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                        .build()
                                        .getAsBitmap(new BitmapRequestListener() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                isChequeTwoUploaded = true;
                                                uploadCheque2Box2ImageTwo.setImageBitmap(bitmap);
                                                getLoanDetailsButtonvalidation();

                                            }
                                            @Override
                                            public void onError(ANError error) {
                                                isChequeTwoUploaded = false;
                                            }
                                        });
                            } else {
                                isChequeTwoUploaded = true;
                                getLoanDetailsButtonvalidation();
                                uploadCheque2Box2ImageTwo.setImageDrawable(activity.getResources().getDrawable(R.mipmap.new_pdf_image));
                            }


                        }
                    }


                    Log.d("LoanAmount", loanAmount + "--");
                    Log.d("LoanTenurePeriod", loanTenurePeriod + "");
                    Log.d("PDC_UDC_Number", pdc_udc_number + "");
                    Log.d("ChequeNo_first", chequeNumberFirst + "");
                    Log.d("Bank_first", bankNumberFirst + "");
                    Log.d("Branch_first", branchFirst + "");
                    Log.d("Branch_first", branchFirst + "");
                    Log.d("additionalDetailsIdd", additionalDetailsIdd + "");

                    if (loanAmount.equals("") || loanTenurePeriod.equals("")) {
                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                        submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
                        submit_btn_getLoan_detail.setEnabled(false);
                    } else {

                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                        submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                        submit_btn_getLoan_detail.setEnabled(true);
                    }

                } catch (JSONException ex) {

                    Log.d("GetLoanDetailsexx", ex.getMessage() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (Method.equals("GetLoanDeleteLoanDoc")) {
                try {
                    getLoanDetails(AppInfo.ekycApplicationId);
                    Log.d("GetLoanDeleteLoanDoc", "Chla");
                    JSONObject mainObj = new JSONObject(Data).getJSONObject("data");
                    if (mainObj.getString("ResponseMessage").equals("Success")) {
                        getLoan_d_bottom.setVisibility(View.GONE);


                        if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                            submit_btn_additional_detail.setVisibility(View.GONE);
                        } else {
                            submit_btn_additional_detail.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException ex) {

                    Log.d("GetLoanDetailsexx", ex.getMessage() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (Method.equals("getCityFromPostal")) {

                try {

                    JSONObject mainObj = new JSONObject(Data);
                    if (mainObj.getString("Status").equals("Success")) {
                        JSONArray postOffice = mainObj.getJSONArray("PostOffice");
                        if (postOffice.length() > 0) {
                            JSONObject cityObject = postOffice.getJSONObject(0);
                            String cityName = cityObject.getString("District");
                            String stateName = cityObject.getString("State");
                            userCityEditText.setText(cityName);
                            userStateEditText.setText(stateName);
                            userCityEditText.setEnabled(false);
                            userStateEditText.setEnabled(false);
                        }
                    }else{
                        ShowMessage.showErrorMessage(mainObj.optString("message"),
                                getActivity());
                    }
                } catch (JSONException ex) {

                }
            } else if (Method.equals("fillEquityFormNew")) {
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    if (mainObj.has("status")) {

                        if (mainObj.getString("status").equals("success")) {
                            String Equity_url = mainObj.getString("data");
                            download(Equity_url);
                        }}
                }catch(JSONException ex) {
                }
            }else if (Method.equals("fillEquityFormforPhysical")) {
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    if (mainObj.has("status")) {
                        if(mainObj.getString("status").equals("success")) {
                            String Equity_url = mainObj.getString("data");
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage("Downloading Application...");
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                            download_forPhysicalDoc(Equity_url);
                        } }
                }catch(JSONException ex) {
                }
            }else if (Method.equals("callAddtionalData")) {
                try{
                    JSONObject mainObj = new JSONObject(Data);
                    System.out.println("ADDITIONALDATAdurgesh" + mainObj.toString());
                    if (mainObj.has("status")) {
                        if (mainObj.getString("status").equals("success")) {
                            System.out.println("datafromgetapi" + mainObj.toString());
                            JSONObject dataob = mainObj.getJSONObject("data");
                            String FATCA_IsUsPerson = dataob.optString("FATCA_IsUsPerson");
                            String FATCA_TaxResidence = dataob.optString("FATCA_TaxResidence");
                            String FATCA_CountryOfCitizenship = dataob.optString("FATCA_CountryOfCitizenship");
                            if(FATCA_IsUsPerson.equals("No")) {
                                is_a_us_person_YES.setChecked(false);
                                is_a_us_person_NO.setChecked(true);
                            }else{
                                is_a_us_person_YES.setChecked(true);
                                is_a_us_person_NO.setChecked(false);
                            }
                            if(FATCA_TaxResidence.equals("India")) {
                                specify_country_residence_INDIA.setChecked(true);
                                specify_country_residence_OTHER.setChecked(false);
                            }else{
                                specify_country_residence_INDIA.setChecked(false);
                                specify_country_residence_OTHER.setChecked(true);
                            }
                            if(FATCA_CountryOfCitizenship.equals("India")) {
                                specify_country_citizenhip_INDIA.setChecked(true);
                                specify_country_citizenhip_OTHER.setChecked(false);
                            } else {
                                specify_country_citizenhip_INDIA.setChecked(false);
                                specify_country_citizenhip_OTHER.setChecked(true);
                            }
                            String PAST_ActionsTaken = dataob.optString("PAST_ActionsTaken");
                            String PAST_ActionsTakenValue = dataob.optString("PAST_ActionsTakenValue");

                            if (PAST_ActionsTaken.equals("No")) {
                                past_actions_NO.setChecked(true);
                                past_actions_YES.setChecked(false);
                            }else{
                                past_actions_NO.setChecked(false);
                                past_actions_YES.setChecked(true);
                                past_action_specification_edt.setText(PAST_ActionsTakenValue);
                            }

                            String SB_IsAnotherAuthorisedDealing = dataob.optString("SB_IsAnotherAuthorisedDealing");
                            if (SB_IsAnotherAuthorisedDealing.equals("No")) {
                                sub_broker_NO.setChecked(true);
                                sub_broker_YES.setChecked(false);

                            } else {
                                sub_broker_NO.setChecked(false);
                                sub_broker_YES.setChecked(true);
                                String SB_NameOfStockBroker = dataob.getString("SB_NameOfStockBroker");
                                String SB_NameOfAuthorisedPersonSubBroker = dataob.getString("SB_NameOfAuthorisedPersonSubBroker");
                                String SB_NameOfExchange = dataob.getString("SB_NameOfExchange");
                                String SB_ClientCode = dataob.getString("SB_ClientCode");
                                String SB_DetailsOfDisputes = dataob.getString("SB_DetailsOfDisputes");
                                String SB_IsMemberOrSubBrokerOrAPOfAnyExchange = dataob.getString("SB_IsMemberOrSubBrokerOrAPOfAnyExchange");
                                sub_broker_name_edt.setText(SB_NameOfStockBroker);
                                sub_broker_authorised_person_edt.setText(SB_NameOfAuthorisedPersonSubBroker);
                                sub_broker_exchange_name_edt.setText(SB_NameOfExchange);
                                sub_broker_client_code_edt.setText(SB_ClientCode);
                                sub_broker_dues_edt.setText(SB_DetailsOfDisputes);

                                if (SB_IsMemberOrSubBrokerOrAPOfAnyExchange.equals("No")) {
                                    //sebi_registration_group_NO.setChecked(true);
                                    sebi_registration_group.check(R.id.sebi_registration_group_NO);

                                } else {
                                    sebi_registration_group.check(R.id.sebi_registration_group_YES);
                                    sebi_registration_group_Number_edt.setText(dataob.getString("SB_SEBIRegistrationNumber"));
                                }
                            }
                            String SI_ContractNoteHoldingTransactionStatement = dataob.optString("SI_ContractNoteHoldingTransactionStatement");

                            if (SI_ContractNoteHoldingTransactionStatement.equals("Electronically")) {
                                review_contract_electronically.setChecked(true);
                            } else {
                                review_contract_physically.setChecked(true);

                            }
                            String AcOpenLocation = dataob.optString("AcOpenLocation");

                            if (AcOpenLocation.equals("1")) {

                                radio_other.setChecked(true);
                                AdditionalObj.put("AcOpenLocation", "1");

                            }else if (AcOpenLocation.equals("NSDL")) {

                                radio_nsdl.setChecked(true);

                            }else if (AcOpenLocation.equals("CDSL")) {
                                radio_cdsl.setChecked(true);
                            }


                            String SI_ReceiveDeliveryInstructionSlip = dataob.optString("SI_ReceiveDeliveryInstructionSlip");
                            if (SI_ReceiveDeliveryInstructionSlip.equals("No")) {
                                review_delivery_NO.setChecked(true);
                            } else {
                                review_delivery_YES.setChecked(true);
                            }

                            String SI_ShareEmailIdWithRegistrar = dataob.optString("SI_ShareEmailIdWithRegistrar");
                            if (SI_ShareEmailIdWithRegistrar.equals("Yes")) {
                                shareEmail_YES.setChecked(true);
                            } else {
                                shareEmail_NO.setChecked(true);
                            }

                            String SI_ReceiveAnnualReport = dataob.optString("SI_ReceiveAnnualReport");
                            if (SI_ReceiveAnnualReport.equals("Electronically")) {
                                recieve_annual_report_electronically.setChecked(true);
                            } else if (SI_ReceiveAnnualReport.equals("Physically")) {
                                recieve_annual_report_physically.setChecked(true);
                            }else {
                                recieve_annual_report_both.setChecked(true);
                            }

                            String SI_ReceiveDPAccountsStatement = dataob.optString("SI_ReceiveDPAccountsStatement");
                            if (SI_ReceiveDPAccountsStatement.equals("As per SEBI regulation")) {
                                receive_DP_accounts_as_per_sebi.setChecked(true);
                            } else if (SI_ReceiveDPAccountsStatement.equals("Monthly")) {

                                receive_DP_accounts_monthly.setChecked(true);
                            } else if (SI_ReceiveDPAccountsStatement.equals("Fortnightly")) {
                                receive_DP_accounts_forthnightly.setChecked(true);
                            } else {
                                receive_DP_accounts_weekly.setChecked(true);
                            }

                            String SI_DeclareMobileNumber = dataob.optString("SI_DeclareMobileNumber");

                            if(SI_DeclareMobileNumber.equals("Self")) {
                                declare_mobile_number_belong_to_self.setChecked(true);
                            }else if (SI_DeclareMobileNumber.equals("Spouse")) {

                                declare_mobile_number_belong_to_spouse.setChecked(true);
                            }else if (SI_DeclareMobileNumber.equals("Child")) {
                                declare_mobile_number_belong_to_child.setChecked(true);
                            }else {
                                declare_mobile_number_belong_to_parant.setChecked(true);
                            }
                            String SI_DeclareEmailAddress = dataob.optString("SI_DeclareEmailAddress");
                            if (SI_DeclareEmailAddress.equals("Yes")) {
                                avail_transaction_using_secured_YES.setChecked(true);
                            }else {
                                avail_transaction_using_secured_NO.setChecked(true);
                            }
                            String Credit = dataob.optString("Credit");
                            if(Credit.equals("Yes")) {
                                instruction_dp_to_recive_every_cradit_yes.setChecked(true);
                            }else {
                                instruction_dp_to_recive_every_cradit_NO.setChecked(true);
                            }
                            String IsNominee =dataob.optString("IsNominee");
                            String NomineeDetails = dataob.optString("NomineeDetails");
                            if(IsNominee.equals("Yes")&&!NomineeDetails.equals("null")){
                                radioyesbutton_yes.setChecked(true);
                                radionobutton_no.setChecked(false);
                                JSONArray jsonArray=dataob.getJSONArray("NomineeDetails");
                                //Toast.makeText(getActivity(), "Length=>"+jsonArray.length(), Toast.LENGTH_LONG).show();
                                if(jsonArray.length()==1){
                                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                                    ll_nominesecondlayout.setVisibility(View.GONE);
                                    ll_nominethirdlayout.setVisibility(View.GONE);
                                    isnomineereadable1=true;

                                    JSONObject jsonObjects0=jsonArray.getJSONObject(0);
                                    nomineeId1=jsonObjects0.getString("NomineeId");
                                    String image_url=jsonObjects0.getString("NomineeImage");

                                    if(!image_url.equals("")){
                                     exist_nomineimage1=true;
                                    }
                                    Picasso.get().load(image_url).into(nominee_oneImageview);
                                    AddressSameAsAccountHolder=jsonObjects0.getString("AddressSameAsAccountHolder");
                                    if(AddressSameAsAccountHolder.equals("Yes")){
                                        checkBoxaddress.setChecked(true);
                                        first_Nominee_Address=true;
                                        nominee_address_different_news.setVisibility(View.GONE);
                                    }else{
                                        first_Nominee_Address=false;
                                        nominee_address_different_news.setVisibility(View.VISIBLE);
                                        checkBoxaddress.setChecked(false);
                                    }

                                    edt_firstnominee.setText(jsonObjects0.getString("NomineeName"));
                                    edtpercentagesharing.setText(jsonObjects0.getString("SharePercentage"));
                                    times=jsonObjects0.getString("DOB");
                                    inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                    OutPutFormat = "dd/MM/yyyy";
                                    convertedDate = formatDate(times, inputFormat, OutPutFormat);

                                    formate1="yyyy-MM-dd'T'HH:mm:ss";
                                    formate2="yyyy-MM-dd";
                                    convertedDate1 = formatDate(times, formate1, formate2);
                                    DOB1=convertedDate1;

                                    dobofnominee1.setText(convertedDate);
                                    relationshipapplicant.setText(jsonObjects0.getString("RelationshipWithNominee"));
                                    addone.setText(jsonObjects0.getString("NomineeAddress1"));
                                    addtwo.setText(jsonObjects0.getString("NomineeAddress2"));
                                    addthree.setText(jsonObjects0.getString("NomineeAddress3"));
                                    addstate.setText(jsonObjects0.getString("NomineeState"));
                                    addcity.setText(jsonObjects0.getString("NomineeCity"));
                                    addpincode.setText(jsonObjects0.getString("NomineePinCode"));
                                    document_typeNominee_1.setText(jsonObjects0.getString("NomineeDocumentType"));
                                    boolean isMinor1=jsonObjects0.getBoolean("IsMinor");

                                    if(isMinor1){
                                        guardiandetailslayout.setVisibility(View.VISIBLE);
                                        isminornominee=true;
                                        String image_1=jsonObjects0.getString("GuardiansImage");

                                         if(!image_1.equals("")){
                                             exist_gurdianimage1=true;
                                         }
                                         Picasso.get().load(image_1).into(gurdian_imageview);
                                         edt_gurdiannameone.setText(jsonObjects0.getString("GuardiansName"));
                                         GuardianAddressSameAsAccountHolder=jsonObjects0.getString("GuardianAddressSameAsAccountHolder");
                                        if(GuardianAddressSameAsAccountHolder.equals("Yes")){
                                            gurdianaddresscheckbox.setChecked(true);
                                            gurdiansaddressselcted=true;
                                            gurdian_address1.setVisibility(View.GONE);
                                        }else{
                                            gurdianaddresscheckbox.setChecked(false);
                                            gurdiansaddressselcted=false;
                                            gurdian_address1.setVisibility(View.VISIBLE);
                                        }
                                        edt_gurdianaddress_one.setText(jsonObjects0.getString("GuardiansAddress1"));
                                        edt_gurdianaddress_two.setText(jsonObjects0.getString("GuardiansAddress2"));
                                        edt_gurdianaddress_three.setText(jsonObjects0.getString("GuardiansAddress3"));
                                        txt_gurdian_city.setText(jsonObjects0.getString("GuardianCity"));
                                        relationshipapplicant_guardian.setText(jsonObjects0.getString("RelationshipWithGuardian"));
                                        editgurdianpincode.setText(jsonObjects0.getString("GuardianPinCode"));
                                        document_typeNomineeGurdian_1.setText(jsonObjects0.getString("GuardianDocumentType"));
                                        Picasso.get().load(jsonObjects0.getString("NomineeImage")).into(nominee_oneImageview);

                                    }else{

                                        guardiandetailslayout.setVisibility(View.GONE);
                                        isminornominee=false;
                                    }

                                }else if(jsonArray.length()==2){
                                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                                    ll_nominesecondlayout.setVisibility(View.VISIBLE);
                                    checkcount=false;
                                    secondnomineactive=true;
                                    ll_nominethirdlayout.setVisibility(View.GONE);
                                    JSONObject jsonObjects0=jsonArray.getJSONObject(0);
                                    String image_url=jsonObjects0.getString("NomineeImage");
                                    isnomineereadable1=true;
                                    if(!image_url.equals("")){
                                        exist_nomineimage1=true;
                                    }
                                    Picasso.get().load(image_url).into(nominee_oneImageview);
                                    nomineeId1=jsonObjects0.getString("NomineeId");
                                    edt_firstnominee.setText(jsonObjects0.getString("NomineeName"));
                                    AddressSameAsAccountHolder=jsonObjects0.getString("AddressSameAsAccountHolder");
                                    if(AddressSameAsAccountHolder.equals("Yes")){
                                        checkBoxaddress.setChecked(true);
                                        first_Nominee_Address=true;
                                        nominee_address_different_news.setVisibility(View.GONE);
                                    }else{
                                        first_Nominee_Address=false;
                                        nominee_address_different_news.setVisibility(View.VISIBLE);
                                        checkBoxaddress.setChecked(false);
                                    }
                                    edtpercentagesharing.setText(jsonObjects0.getString("SharePercentage"));
                                    times=jsonObjects0.getString("DOB");
                                    inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                    OutPutFormat = "dd-MM-yyyy";
                                    convertedDate = formatDate(times, inputFormat, OutPutFormat);
                                    formate1="yyyy-MM-dd'T'HH:mm:ss";
                                    formate2="yyyy-MM-dd";
                                    convertedDate1 = formatDate(times, formate1, formate2);
                                    DOB1=convertedDate1;
                                    dobofnominee1.setText(convertedDate);
                                    document_typeNominee_1.setText(jsonObjects0.getString("NomineeDocumentType"));
                                    relationshipapplicant.setText(jsonObjects0.getString("RelationshipWithNominee"));
                                    addone.setText(jsonObjects0.getString("NomineeAddress1"));
                                    addtwo.setText(jsonObjects0.getString("NomineeAddress2"));
                                    addthree.setText(jsonObjects0.getString("NomineeAddress3"));
                                    addstate.setText(jsonObjects0.getString("NomineeState"));
                                    addcity.setText(jsonObjects0.getString("NomineeCity"));
                                    addpincode.setText(jsonObjects0.getString("NomineePinCode"));
                                    boolean isMinor1=jsonObjects0.getBoolean("IsMinor");

                                    if(isMinor1){
                                        guardiandetailslayout.setVisibility(View.VISIBLE);
                                        isminornominee=true;
                                        String image_1=jsonObjects0.getString("GuardiansImage");
                                        if(!image_1.equals("")){
                                            exist_gurdianimage1=true;
                                        }
                                        Picasso.get().load(image_1).into(gurdian_imageview);
                                        GuardianAddressSameAsAccountHolder=jsonObjects0.getString("GuardianAddressSameAsAccountHolder");
                                        if(GuardianAddressSameAsAccountHolder.equals("Yes")){
                                            gurdianaddresscheckbox.setChecked(true);
                                            gurdiansaddressselcted=true;
                                            gurdian_address1.setVisibility(View.GONE);
                                        }else{
                                            gurdianaddresscheckbox.setChecked(false);
                                            gurdiansaddressselcted=false;
                                            gurdian_address1.setVisibility(View.VISIBLE);
                                        }

                                        edt_gurdiannameone.setText(jsonObjects0.getString("GuardiansName"));
                                        edt_gurdianaddress_one.setText(jsonObjects0.getString("GuardiansAddress1"));
                                        edt_gurdianaddress_two.setText(jsonObjects0.getString("GuardiansAddress2"));
                                        edt_gurdianaddress_three.setText(jsonObjects0.getString("GuardiansAddress3"));
                                        txt_gurdian_city.setText(jsonObjects0.getString("GuardianCity"));
                                        relationshipapplicant_guardian.setText(jsonObjects0.getString("RelationshipWithGuardian"));
                                        editgurdianpincode.setText(jsonObjects0.getString("GuardianPinCode"));
                                        Picasso.get().load(jsonObjects0.getString("NomineeImage")).into(nominee_oneImageview);
                                        document_typeNomineeGurdian_1.setText(jsonObjects0.getString("GuardianDocumentType"));
                                    }else{
                                        guardiandetailslayout.setVisibility(View.GONE);
                                        isminornominee=false;
                                    }

                                    JSONObject jsonObject1=jsonArray.getJSONObject(1);
                                    String image_urll=jsonObject1.getString("NomineeImage");
                                    isnomineereadable2=true;
                                    if(!image_urll.equals("")){
                                        exist_nomineimage2=true;
                                    }
                                    Picasso.get().load(image_urll).into(second_nominee_image);
                                    nomineeId2=jsonObject1.getString("NomineeId");
                                    edt_name_of_nominee2.setText(jsonObject1.getString("NomineeName"));
                                    AddressSameAsAccountHolder=jsonObject1.getString("AddressSameAsAccountHolder");
                                    if(AddressSameAsAccountHolder.equals("Yes")){
                                        checkBoxaddress_2.setChecked(true);
                                        nominee_address_different_news2.setVisibility(View.GONE);
                                        secondn_Nominee_Address=true;
                                    }else{
                                        gurdian_address2.setVisibility(View.VISIBLE);
                                        secondn_Nominee_Address=false;
                                        checkBoxaddress_2.setChecked(false);
                                    }
                                    edtpercentagesharing2.setText(jsonObject1.getString("SharePercentage"));
                                    times=jsonObject1.getString("DOB");
                                    inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                    OutPutFormat = "dd-MM-yyyy";
                                    convertedDate = formatDate(times, inputFormat, OutPutFormat);
                                    formate1="yyyy-MM-dd'T'HH:mm:ss";
                                    formate2="yyyy-MM-dd";
                                    convertedDate1 = formatDate(times, formate1, formate2);
                                    DOB2=convertedDate1;
                                    dobofnominee2.setText(convertedDate);


                                    relationshipapplicant2.setText(jsonObject1.getString("RelationshipWithNominee"));
                                    addres_one_of_nominee2.setText(jsonObject1.getString("NomineeAddress1"));
                                    addres_two_of_nominee2.setText(jsonObject1.getString("NomineeAddress2"));
                                    addres_three_of_nominee2.setText(jsonObject1.getString("NomineeAddress3"));
                                    addstate2.setText(jsonObject1.getString("NomineeState"));
                                    addcity2.setText(jsonObject1.getString("NomineeCity"));
                                    addpincode2.setText(jsonObject1.getString("NomineePinCode"));
                                    document_typeNominee_2.setText(jsonObject1.getString("NomineeDocumentType"));
                                    boolean isMinor2=jsonObject1.getBoolean("IsMinor");
                                    if(isMinor2){
                                        guardiandetailslayout_secondNominee.setVisibility(View.VISIBLE);
                                        is_Second_minornominee=true;
                                        String image_1=jsonObject1.getString("GuardiansImage");
                                        if(!image_1.equals("")){
                                            exist_gurdianimage2=true;
                                        }
                                        Picasso.get().load(image_1).into(gurdian_Second_imageview);
                                        GuardianAddressSameAsAccountHolder=jsonObject1.getString("GuardianAddressSameAsAccountHolder");
                                        if(GuardianAddressSameAsAccountHolder.equals("Yes")){
                                            gurdianaddresscheckbox_ofsecondnominee.setChecked(true);
                                            gurdiansaddressselcted_ofsecondnominee=true;
                                            gurdian_address2.setVisibility(View.GONE);

                                        }else{
                                            gurdianaddresscheckbox_ofsecondnominee.setChecked(false);
                                            gurdiansaddressselcted_ofsecondnominee=false;
                                            gurdian_address2.setVisibility(View.VISIBLE);
                                        }
                                        edt_gurdianname2.setText(jsonObject1.getString("GuardiansName"));
                                        edt_gurdianaddress2.setText(jsonObject1.getString("GuardiansAddress1"));
                                        edt_gurdianaddress_two2.setText(jsonObject1.getString("GuardiansAddress2"));
                                        edt_gurdianaddress_three2.setText(jsonObject1.getString("GuardiansAddress3"));
                                        txt_gurdian_city2.setText(jsonObject1.getString("GuardianCity"));
                                        relationshipapplicant_guardian2.setText(jsonObject1.getString("RelationshipWithGuardian"));
                                        edittextpincode2.setText(jsonObject1.getString("GuardianPinCode"));
                                        document_typeNomineeGurdian_2.setText(jsonObject1.getString("GuardianDocumentType"));

                                    }else{
                                        guardiandetailslayout_secondNominee.setVisibility(View.GONE);
                                        is_Second_minornominee=false;
                                    }
                                }else if(jsonArray.length()==3){
                                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                                    ll_nominesecondlayout.setVisibility(View.VISIBLE);
                                    checkcount=false;
                                    secondnomineactive=true;
                                    ll_nominethirdlayout.setVisibility(View.VISIBLE);
                                    checkcount = true;
                                    thirdnomineactive=true;
                                    isnomineereadable1=true;
                                    JSONObject jsonObjects0=jsonArray.getJSONObject(0);
                                    nomineeId1=jsonObjects0.getString("NomineeId");
                                    String image_url=jsonObjects0.getString("NomineeImage");
                                    if(!image_url.equals("")){
                                        exist_nomineimage1=true;
                                    }

                                    Picasso.get().load(image_url).into(nominee_oneImageview);
                                    edt_firstnominee.setText(jsonObjects0.getString("NomineeName"));
                                    AddressSameAsAccountHolder=jsonObjects0.getString("AddressSameAsAccountHolder");

                                    if(AddressSameAsAccountHolder.equals("Yes")){
                                        checkBoxaddress.setChecked(true);
                                        first_Nominee_Address=true;
                                        nominee_address_different_news.setVisibility(View.GONE);
                                    }else{
                                        first_Nominee_Address=false;
                                        nominee_address_different_news.setVisibility(View.VISIBLE);
                                        checkBoxaddress.setChecked(false);
                                    }
                                    edtpercentagesharing.setText(jsonObjects0.getString("SharePercentage"));
                                    times=jsonObjects0.getString("DOB");
                                    inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                    OutPutFormat = "dd-MM-yyyy";
                                    convertedDate = formatDate(times, inputFormat, OutPutFormat);

                                    formate1="yyyy-MM-dd'T'HH:mm:ss";
                                    formate2="yyyy-MM-dd";
                                    convertedDate1 = formatDate(times, formate1, formate2);
                                    DOB1=convertedDate1;
                                    dobofnominee1.setText(convertedDate);
                                    relationshipapplicant.setText(jsonObjects0.getString("RelationshipWithNominee"));
                                    addone.setText(jsonObjects0.getString("NomineeAddress1"));
                                    addtwo.setText(jsonObjects0.getString("NomineeAddress2"));
                                    addthree.setText(jsonObjects0.getString("NomineeAddress3"));
                                    addstate.setText(jsonObjects0.getString("NomineeState"));
                                    addcity.setText(jsonObjects0.getString("NomineeCity"));
                                    addpincode.setText(jsonObjects0.getString("NomineePinCode"));
                                    document_typeNominee_1.setText(jsonObjects0.getString("NomineeDocumentType"));
                                    boolean isMinor1=jsonObjects0.getBoolean("IsMinor");
                                    if(isMinor1){
                                        guardiandetailslayout.setVisibility(View.VISIBLE);
                                        isminornominee=true;
                                        String image_1=jsonObjects0.getString("GuardiansImage");
                                        if(!image_1.equals("")){
                                            exist_gurdianimage1=true;
                                        }
                                        Picasso.get().load(image_1).into(gurdian_imageview);
                                        GuardianAddressSameAsAccountHolder=jsonObjects0.getString("GuardianAddressSameAsAccountHolder");
                                        if(GuardianAddressSameAsAccountHolder.equals("Yes")){
                                            gurdianaddresscheckbox.setChecked(true);
                                            gurdiansaddressselcted=true;
                                            gurdian_address1.setVisibility(View.GONE);
                                        }else{
                                            gurdianaddresscheckbox.setChecked(false);
                                            gurdiansaddressselcted=false;
                                            gurdian_address1.setVisibility(View.VISIBLE);
                                        }
                                        edt_gurdiannameone.setText(jsonObjects0.getString("GuardiansName"));
                                        edt_gurdianaddress_one.setText(jsonObjects0.getString("GuardiansAddress1"));
                                        edt_gurdianaddress_two.setText(jsonObjects0.getString("GuardiansAddress2"));
                                        edt_gurdianaddress_three.setText(jsonObjects0.getString("GuardiansAddress3"));
                                        txt_gurdian_city.setText(jsonObjects0.getString("GuardianCity"));
                                        relationshipapplicant_guardian.setText(jsonObjects0.getString("RelationshipWithGuardian"));
                                        editgurdianpincode.setText(jsonObjects0.getString("GuardianPinCode"));
                                        Picasso.get().load(jsonObjects0.getString("NomineeImage")).into(nominee_oneImageview);
                                        document_typeNomineeGurdian_1.setText(jsonObjects0.getString("GuardianDocumentType"));

                                    }else{
                                        guardiandetailslayout.setVisibility(View.GONE);
                                        isminornominee=false;
                                    }
                                    JSONObject jsonObject1=jsonArray.getJSONObject(1);

                                    isnomineereadable2=true;
                                    String image_urll=jsonObject1.getString("NomineeImage");

                                    if(!image_urll.equals("")){
                                        exist_nomineimage2=true;
                                    }
                                    Picasso.get().load(image_urll).into(second_nominee_image);
                                    nomineeId2=jsonObject1.getString("NomineeId");
                                    edt_name_of_nominee2.setText(jsonObject1.getString("NomineeName"));
                                    AddressSameAsAccountHolder=jsonObject1.getString("AddressSameAsAccountHolder");

                                    if(AddressSameAsAccountHolder.equals("Yes")){
                                        checkBoxaddress_2.setChecked(true);
                                        secondn_Nominee_Address=true;
                                        nominee_address_different_news2.setVisibility(View.GONE);

                                    }else{
                                        secondn_Nominee_Address=false;
                                        nominee_address_different_news2.setVisibility(View.VISIBLE);
                                        checkBoxaddress_2.setChecked(false);

                                    }
                                    edtpercentagesharing2.setText(jsonObject1.getString("SharePercentage"));
                                    times=jsonObject1.getString("DOB");
                                    inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                    OutPutFormat = "dd-MM-yyyy";
                                    convertedDate = formatDate(times, inputFormat, OutPutFormat);


                                    formate1="yyyy-MM-dd'T'HH:mm:ss";
                                    formate2="yyyy-MM-dd";
                                    convertedDate1 = formatDate(times, formate1, formate2);

                                    DOB2=convertedDate1;
                                    dobofnominee2.setText(convertedDate);
                                    relationshipapplicant2.setText(jsonObject1.getString("RelationshipWithNominee"));
                                    addres_one_of_nominee2.setText(jsonObject1.getString("NomineeAddress1"));
                                    addres_two_of_nominee2.setText(jsonObject1.getString("NomineeAddress2"));
                                    addres_three_of_nominee2.setText(jsonObject1.getString("NomineeAddress3"));
                                    addstate2.setText(jsonObject1.getString("NomineeState"));
                                    addcity2.setText(jsonObject1.getString("NomineeCity"));
                                    addpincode2.setText(jsonObject1.getString("NomineePinCode"));
                                    boolean isMinor2=jsonObject1.getBoolean("IsMinor");
                                    document_typeNominee_2.setText(jsonObject1.getString("NomineeDocumentType"));

                                    if(isMinor2){
                                        guardiandetailslayout_secondNominee.setVisibility(View.VISIBLE);
                                        is_Second_minornominee=true;
                                        String image_1=jsonObject1.getString("GuardiansImage");
                                        if(!image_1.equals("")){
                                            exist_gurdianimage2=true;
                                        }
                                        Picasso.get().load(image_1).into(gurdian_Second_imageview);
                                        GuardianAddressSameAsAccountHolder=jsonObject1.getString("GuardianAddressSameAsAccountHolder");
                                        if(GuardianAddressSameAsAccountHolder.equals("Yes")){
                                            gurdianaddresscheckbox.setChecked(true);
                                            gurdiansaddressselcted=true;
                                            gurdian_address1.setVisibility(View.GONE);

                                        }else{
                                            gurdianaddresscheckbox.setChecked(false);
                                            gurdiansaddressselcted=false;
                                            gurdian_address1.setVisibility(View.VISIBLE);
                                        }
                                        edt_gurdianname2.setText(jsonObject1.getString("GuardiansName"));
                                        edt_gurdianaddress2.setText(jsonObject1.getString("GuardiansAddress1"));
                                        edt_gurdianaddress_two2.setText(jsonObject1.getString("GuardiansAddress2"));
                                        edt_gurdianaddress_three2.setText(jsonObject1.getString("GuardiansAddress3"));
                                        txt_gurdian_city2.setText(jsonObject1.getString("GuardianCity"));
                                        relationshipapplicant_guardian2.setText(jsonObject1.getString("RelationshipWithGuardian"));
                                        edittextpincode2.setText(jsonObject1.getString("GuardianPinCode"));
                                        document_typeNomineeGurdian_2.setText(jsonObject1.getString("GuardianDocumentType"));
                                       }else{
                                        guardiandetailslayout_secondNominee.setVisibility(View.GONE);
                                        is_Second_minornominee=false;
                                    }
                                    JSONObject jsonObject2=jsonArray.getJSONObject(2);
                                    String image_urlll=jsonObject2.getString("NomineeImage");
                                    if(!image_urlll.equals("")){
                                        exist_nomineimage3=true;
                                    }
                                    Picasso.get().load(image_urlll).into(Third_nominee_image);
                                    nomineeId3=jsonObject2.getString("NomineeId");
                                    edt_nameofnominee3.setText(jsonObject2.getString("NomineeName"));
                                    AddressSameAsAccountHolder=jsonObject2.getString("AddressSameAsAccountHolder");
                                    if(AddressSameAsAccountHolder.equals("Yes")){
                                        checkBoxaddress3.setChecked(true);
                                        third_Nominee_Address=true;
                                        nominee_address_different_news3.setVisibility(View.GONE);

                                    }else{
                                        third_Nominee_Address=false;
                                        nominee_address_different_news3.setVisibility(View.VISIBLE);
                                        checkBoxaddress3.setChecked(false);
                                    }
                                    edtpercentagesharing3.setText(jsonObject2.getString("SharePercentage"));
                                    times=jsonObject2.getString("DOB");
                                    inputFormat = "yyyy-MM-dd'T'HH:mm:ss";
                                    OutPutFormat = "dd-MM-yyyy";
                                    convertedDate = formatDate(times, inputFormat, OutPutFormat);


                                    formate1="yyyy-MM-dd'T'HH:mm:ss";
                                    formate2="yyyy-MM-dd";
                                    convertedDate1 = formatDate(times, formate1, formate2);
                                    DOB3=convertedDate1;
                                    isnomineereadable3=true;

                                    dobofnominee3.setText(convertedDate);
                                    relationshipapplicant3.setText(jsonObject2.getString("RelationshipWithNominee"));
                                    addone3.setText(jsonObject2.getString("NomineeAddress1"));
                                    addtwo3.setText(jsonObject2.getString("NomineeAddress2"));
                                    addthree3.setText(jsonObject2.getString("NomineeAddress3"));
                                    addstate3.setText(jsonObject2.getString("NomineeState"));
                                    addcity3.setText(jsonObject2.getString("NomineeCity"));
                                    addpincode3.setText(jsonObject2.getString("NomineePinCode"));
                                    document_typeNominee_3.setText(jsonObject2.getString("NomineeDocumentType"));
                                    boolean isMinor3=jsonObject2.getBoolean("IsMinor");
                                    if(isMinor3){
                                        guardiandetailslayout_thirdNominee.setVisibility(View.VISIBLE);
                                        is_Third_minornominee=true;
                                        String image_1=jsonObject1.getString("GuardiansImage");
                                        if(!image_1.equals("")){
                                            exist_gurdianimage3=true;
                                        }
                                        Picasso.get().load(image_1).into(gurdian_imageviewthird);
                                        GuardianAddressSameAsAccountHolder=jsonObject2.getString("GuardianAddressSameAsAccountHolder");
                                        if(GuardianAddressSameAsAccountHolder.equals("Yes")){
                                            checkBoxgurdianaddress3.setChecked(true);
                                            gurdiansaddressselcted_ofthirdnominee=true;
                                            gurdian_address3.setVisibility(View.GONE);
                                        }else{
                                            checkBoxgurdianaddress3.setChecked(false);
                                            gurdiansaddressselcted_ofthirdnominee=false;
                                            gurdian_address3.setVisibility(View.VISIBLE);
                                        }
                                        edt_gurdianname3.setText(jsonObject2.getString("GuardiansName"));
                                        edt_gurdianaddress_one3.setText(jsonObject2.getString("GuardiansAddress1"));
                                        edt_gurdianaddress_two3.setText(jsonObject2.getString("GuardiansAddress2"));
                                        edt_gurdianaddress_three3.setText(jsonObject2.getString("GuardiansAddress3"));
                                        txt_gurdian_city3.setText(jsonObject2.getString("GuardianCity"));
                                        relationshipapplicant_guardian3.setText(jsonObject2.getString("RelationshipWithGuardian"));
                                        edittextpincode3.setText(jsonObject2.getString("GuardianPinCode"));
                                        document_typeNomineeGurdian_3.setText(jsonObject2.getString("GuardianDocumentType"));
                                    }else{
                                        guardiandetailslayout_thirdNominee.setVisibility(View.GONE);
                                        is_Third_minornominee=false;
                                    }}

                            }else if(IsNominee.equals("No")&&NomineeDetails.equals("null")){
                                radioyesbutton_yes.setChecked(false);
                                radionobutton_no.setChecked(true);
                            }
                            else if(NomineeDetails.equals("null")||NomineeDetails.equals("")){
                                radioyesbutton_yes.setChecked(false);
                                radionobutton_no.setChecked(false);
                            }
                            else{
                                radioyesbutton_yes.setChecked(false);
                                radionobutton_no.setChecked(true);
                            }

                            String PledgeInstructions = dataob.optString("PledgeInstructions");
                            if (PledgeInstructions.equals("Yes")) {
                                instruction_dp_to_accept_all_pledge_YES.setChecked(true);
                            }else {
                                instruction_dp_to_accept_all_pledge_NO.setChecked(true);
                            }
                            String InterestThroughECS = dataob.optString("InterestThroughECS");
                            if (InterestThroughECS.equals("Yes")) {
                                bank_account_as_given_below_through_ECS_YES.setChecked(true);
                            }else{
                                bank_account_as_given_below_through_ECS_NO.setChecked(true);
                            }
                            String DematAccFacility = dataob.optString("DematAccFacility");
                            if(DematAccFacility.equals("Yes")) {
                                basic_services_demat_account_facility_YES.setChecked(true);
                            }else {
                                basic_services_demat_account_facility_NO.setChecked(true);
                            }}
                    }
                }catch (JSONException ex) {
                    System.out.println("ADDITIONALDATA" + ex.getMessage() + "");
                }
            }else if (Method.equals("fillCommodityForm")) {
                try{
                    JSONObject mainObj = new JSONObject(Data);
                    if (mainObj.getString("status").equals("success")) {
                        String Commodity_url = mainObj.getString("data");
                    }
                }catch (JSONException ex) {
                }
            }else if (Method.equals("UploadOnDigio")) {
                try {
                    JSONObject mainObj = new JSONObject(Data);
                    if (mainObj.getString("status").equals("success")) {
                        JSONObject object = mainObj.getJSONObject("data");
                        document_id = object.optString("id");
                        System.out.println("UploadOnDigio_url" + object.toString());
                        if(currentStatu.equals("PendingEsign") || currentStatu.equals("In Progress")) {
                            if (document_id != null) {
                                if (!document_id.equals("null")) {
                                    if(acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    callNewDigio(document_id);
                                }else{
                                    Toast.makeText(getActivity(), " Your Document Id Is Not valid ", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getActivity(), " Your Document Id Is Not valid ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }catch (JSONException ex) {
                    System.out.println("UploadOnDigio_error" + ex.getMessage());
                }


            }else if (Method.equals("uploadDigioPDF")) {
                if(File_download_dialog != null) {
                    if (File_download_dialog.isShowing())
                        File_download_dialog.dismiss();
                }
                try{
                    JSONObject mainObj = new JSONObject(Data);
                    //String documentId = mainObj.getString("id");
                    Digio digio = new Digio();
                    DigioConfig digioConfig = new DigioConfig();
                    digioConfig.setLogo("https://image3.mouthshut.com/images/ImagesR/imageuser_m/2014/8/925596104-9839255-1.jpg?rnd=84212"); //Your company logo
                    digioConfig.setEnvironment(DigioEnvironment.PRODUCTION);
                    //digioConfi//Stage is sandbox
                    digioConfig.setServiceMode(DigioServiceMode.OTP);//FP is fingerprint
                    digioConfig.setAadhaarId(ScanResultAadhar.uid);
                    try{
                        digio.init(getActivity(), digioConfig);
                    }catch(Exception e) {
                    }
                    try {
                        digioDownloaded = false;
                        UserIdentity user = FinalApplicationForm.getUserIdentyObject();
                        digioDocumentId = document_id;
                        AppInfo.digioDocumentId = document_id;
                        digio.esign(document_id, user.getEmailId());
                        System.out.println("UploadOnDigio_url12" + document_id);

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch (JSONException ex) {

                    Toast.makeText(getActivity(), ex.getMessage() + "",
                            Toast.LENGTH_LONG).show();
                    System.out.println("ErrorDigioJSON" + ex.getMessage());

                }

            }else if (Method.equals("getBrokerageByekycId")) {
                try{
                    JSONObject mainObj1 = new JSONObject(Data);
                    System.out.println("getBrokerageByekycId>>>" + "mainObj1" + mainObj1.toString() + "");
                    System.out.println("getBrokerageByekycId" + "bottomSheetBehaviorBrockrage : " + bottomSheetBehaviorBrockrage.getState());
                    if(mainObj1.getString("status").equals("success")) {
                        System.out.println("AfterSuccessObj" + mainObj1.toString() + "");
                        if(mainObj1.get("data") != null || !mainObj1.get("data").equals("null")) {
                            System.out.println("Data is not null" + "");
                            if (mainObj1.get("data") instanceof JSONObject) {

                                JSONObject mainObj = mainObj1.getJSONObject("data");
                                System.out.println("getBrokerageByekycId>>>" + "mainObj" + mainObj.toString() + "");
                                bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_EXPANDED);
                                System.out.println("getBrokerageByekycId" + "bottomSheetBehaviorBrockrage 2 : " + bottomSheetBehaviorBrockrage.getState());
                                String id = mainObj.getString("id");
                                boolean IsPremium = mainObj.getBoolean("IsPremium");
                                System.out.println("getBrokerageByekycId>>>" + "IsPremium" + IsPremium + "");

                                if (IsPremium == true) {
                                    premiumMenu.performClick();
                                    //discountBrokerage.setVisibility(View.GONE);
                                    //premiumBrokerage.setVisibility(View.VISIBLE);
                                    System.out.println("getBrokerageByekycId>>>" + "in premium update : IsPremium" + IsPremium + "");
                                    String comfuturesSlot = mainObj.optString("comfuturesSlot");
                                    unitCommodityfuture.setText(comfuturesSlot);
                                    System.out.println("comfuturesSlot" + comfuturesSlot);
                                    commodity_derivatives_futures_value = mainObj.optString("com_futures");
                                    commodity_derivatives_futures.setText(commodity_derivatives_futures_value);
                                    String comoptionsSlot = mainObj.optString("comoptionsSlot");
                                    unitCommodityoption.setText(comoptionsSlot);
                                    commodity_derivatives_options_value = mainObj.getString("com_options");
                                    commodity_derivatives_options.setText(commodity_derivatives_options_value);
                                    String contract_note = mainObj.optString("contract_note");
                                    currency_derivatives_futures_value = mainObj.optString("cur_future");
                                    currency_derivatives_futures.setText(currency_derivatives_futures_value);
                                    String curfutureSlot = mainObj.optString("curfutureSlot");
                                    unitCurrencyfuture.setText(curfutureSlot);
                                    currency_derivatives_options_value = mainObj.optString("cur_options");
                                    currency_derivatives_options.setText(currency_derivatives_options_value);
                                    String curoptionsSlot = mainObj.optString("curoptionsSlot");
                                    unitCurrencyoption.setText(curoptionsSlot);
                                    derivatives_futures_value = mainObj.optString("der_futures");
                                    derivatives_futures.setText(derivatives_futures_value);
                                    String derfuturesSlot = mainObj.optString("derfuturesSlot");
                                    unitDerivetivefuture.setText(derfuturesSlot);
                                    derivatives_options_value = mainObj.optString("der_options");
                                    derivatives_options.setText(derivatives_options_value);
                                    String deroptionsSlot = mainObj.optString("deroptionsSlot");
                                    unitDerivativeOption.setText(deroptionsSlot);
                                    String ekycApplicationId = mainObj.optString("ekycApplicationId");
                                    stock_delivery_value = mainObj.optString("stockDeliver");
                                    stock_delivery.setText(stock_delivery_value);
                                    String stockDeliverSlot = mainObj.optString("stockDeliverSlot");
                                    unitStockDelivery.setText(stockDeliverSlot);
                                    stock_intraday_value = mainObj.optString("stockIntraday");
                                    stock_intraday.setText(stock_intraday_value);
                                    String stockIntradaySlot = mainObj.optString("stockIntradaySlot");
                                    unitStockIntraday.setText(stockIntradaySlot);
                                    String turnOver = mainObj.optString("turnOver");
                                    turnOverEdit.setText(turnOver);
                                }else if (IsPremium == false) {

                                    discountMenu.performClick();
                                    System.out.println("getBrokerageByekycId>>>" + "in discount update : IsPremium" + IsPremium + "");

                                } else {
                                    premiumMenu.performClick();
                                    System.out.println("getBrokerageByekycId>>>" + "in old brokerage : IsPremium" + IsPremium + "");
                                    String comfuturesSlot = mainObj.optString("comfuturesSlot");
                                    unitCommodityfuture.setText(comfuturesSlot);
                                    System.out.println("comfuturesSlot" + comfuturesSlot);
                                    commodity_derivatives_futures_value = mainObj.optString("com_futures");
                                    commodity_derivatives_futures.setText(commodity_derivatives_futures_value);
                                    String comoptionsSlot = mainObj.optString("comoptionsSlot");
                                    unitCommodityoption.setText(comoptionsSlot);

                                    commodity_derivatives_options_value = mainObj.getString("com_options");
                                    commodity_derivatives_options.setText(commodity_derivatives_options_value);
                                    String contract_note = mainObj.optString("contract_note");
                                    currency_derivatives_futures_value = mainObj.optString("cur_future");
                                    currency_derivatives_futures.setText(currency_derivatives_futures_value);
                                    String curfutureSlot = mainObj.optString("curfutureSlot");
                                    unitCurrencyfuture.setText(curfutureSlot);
                                    currency_derivatives_options_value = mainObj.optString("cur_options");
                                    currency_derivatives_options.setText(currency_derivatives_options_value);
                                    String curoptionsSlot = mainObj.optString("curoptionsSlot");
                                    unitCurrencyoption.setText(curoptionsSlot);
                                    derivatives_futures_value = mainObj.optString("der_futures");
                                    derivatives_futures.setText(derivatives_futures_value);
                                    String derfuturesSlot = mainObj.optString("derfuturesSlot");
                                    unitDerivetivefuture.setText(derfuturesSlot);
                                    derivatives_options_value = mainObj.optString("der_options");
                                    derivatives_options.setText(derivatives_options_value);
                                    String deroptionsSlot = mainObj.optString("deroptionsSlot");
                                    unitDerivativeOption.setText(deroptionsSlot);
                                    String ekycApplicationId = mainObj.optString("ekycApplicationId");
                                    stock_delivery_value = mainObj.optString("stockDeliver");
                                    stock_delivery.setText(stock_delivery_value);
                                    String stockDeliverSlot = mainObj.optString("stockDeliverSlot");
                                    unitStockDelivery.setText(stockDeliverSlot);
                                    stock_intraday_value = mainObj.optString("stockIntraday");
                                    stock_intraday.setText(stock_intraday_value);
                                    String stockIntradaySlot = mainObj.optString("stockIntradaySlot");
                                    unitStockIntraday.setText(stockIntradaySlot);
                                }

                                String turnOver = mainObj.optString("turnOver");
                                turnOverEdit.setText(turnOver);

                                JSONObject documentObj = mainObj.optJSONObject("documents");

                                if (documentObj != null) {
                                    String imageUrl = documentObj.optString("documentBaseUrl") + documentObj.getString("documentUrl");
                                    String additionalDescription = documentObj.getString("additionalDescription");
                                    System.out.println("getBrokerageByekycId>>>" + "imageUrl" + imageUrl + "");
                                    System.out.println("getBrokerageByekycId>>>" + "additionalDescription" + additionalDescription + "");
                                    brokrafeNotes.setText(additionalDescription);
                                    try {
                                        AndroidNetworking.get(imageUrl)
                                                .setTag("brokrage")
                                                .setPriority(Priority.MEDIUM)
                                                .setBitmapMaxHeight(100)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                                                .setBitmapMaxWidth(100)
                                                .doNotCacheResponse()
                                                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                .build()
                                                .getAsBitmap(new BitmapRequestListener() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        System.out.println("getBrokerageByekycId>>>" + "brokrage : bitmap" + bitmap + "");
                                                        documentUploaded = true;
                                                        brokageImg.setImageBitmap(bitmap);
                                                        brokageImg.setVisibility(View.VISIBLE);
                                                        brokTxt.setText("CHANGE CONTRACT NOTE");

                                                    }
                                                    @Override
                                                    public void onError(ANError error) {
                                                        System.out.println("getBrokerageByekycId>>>" + "brokrage : error" + error + "");
                                                        brokageImg.setVisibility(View.GONE);
                                                        brokTxt.setText("UPLOAD OLD CONTRACT NOTE");
                                                    }
                                                });
                                    }catch (Exception ex) {
                                    }
                                }else{
                                    brokageImg.setVisibility(View.GONE);
                                    brokTxt.setText("UPLOAD OLD CONTRACT NOTE");
                                }
                                brokerageAlreadyFound = true;
                                if(isLessThanRequired()) {
                                    //Toast.makeText(getActivity(), "Value less than found", Toast.LENGTH_SHORT).show();
                                    uploadAdditionalDocLayout.setVisibility(View.VISIBLE);
                                } else {
                                    //Toast.makeText(getActivity(), "Value less than not found", Toast.LENGTH_SHORT).show();
                                    uploadAdditionalDocLayout.setVisibility(View.GONE);
                                }
                                if(turnOver.length() == 0) {
                                    uploadAdditionalDocLayout.setVisibility(View.GONE);

                                }else{

                                    uploadAdditionalDocLayout.setVisibility(View.VISIBLE);
                                }

                            } else {
                                System.out.println("Data is null1" + "");
                                getBrokeragefromMaster();
                            }
                        } else {

                            System.out.println("Data is null2" + "");
                            getBrokeragefromMaster();
                        }

                    }

                } catch (JSONException ex) {
                    getBrokeragefromMaster();
                    System.out.println("getBrokerage" + ex.getMessage());

                }


            } else if (Method.equals("getBrokerageByCompanyId")) {

                try {

                    System.out.println("getBrokerageByCompanyId" + Method + "");
                    if (Data != null) {

                        bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_EXPANDED);
                        JSONObject object = new JSONObject(Data);
                        System.out.println("getBrokerageByCompanyId" + "object" + object);

                        JSONArray mainArray = object.getJSONArray("data");

                        System.out.println("getBrokerageByCompanyId" + "mainArrayLength" + mainArray.length() + "");
                        System.out.println("getBrokerageByCompanyId" + "mainArrayLength" + mainArray);
                        for (int a = 0; a < mainArray.length(); a++) {
                            JSONObject mainObj = mainArray.getJSONObject(a);

                            JSONArray array = mainObj.getJSONArray("BrokerageTypeMasters");

                            System.out.println("getBrokerageByCompanyId" + "BrokerageLengh" + array.length() + "");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);


                                String BrokerageTypeId = object1.optString("BrokerageTypeId");
                                System.out.println("getBrokerageByCompanyId" + "BrokerageId" + BrokerageTypeId);

                                if (BrokerageTypeId.equals("1")) {

                                    JSONArray array1 = object1.getJSONArray("BrokerageSubTypeMasters");

                                    for (int j = 0; j < array1.length(); j++) {
                                        JSONObject object2 = array1.getJSONObject(j);
                                        String BrokerageSubTyp = object2.getString("BrokerageSubTypeName");

                                        JSONArray array2 = object2.getJSONArray("BrokerageSubTypCategoryeMasters");
                                        for (int k = 0; k < array2.length(); k++) {
                                            JSONObject object3 = array2.getJSONObject(k);
                                            if (BrokerageSubTyp.equals("Stocks")) {

                                                if (object3.getString("BrokerageSubTypeCategoryName").equals("Delivery")) {
//
                                                    stock_delivery_value = object3.getString("Brokerage");

                                                    unitStockDelivery_value = object3.getString("BrokerageSlot");
                                                    unitStockDelivery.setText(unitStockDelivery_value);


                                                } else if (object3.getString("BrokerageSubTypeCategoryName").equals("Intraday")) {
                                                    stock_intraday_value = object3.getString("Brokerage");

                                                    unitStockintraday_value = object3.getString("BrokerageSlot");

//                                                    unitStockIntraday.setText(unitStockintraday_value);

//                                                    if (unitStockintraday_value.equals("Percentage")) {
//                                                        unitStockIntraday.setText("%");
//                                                    } else {
//                                                        unitStockIntraday.setText(unitStockintraday_value);
//                                                    }
                                                    unitStockIntraday.setText(unitStockintraday_value);

                                                }
                                            } else if (BrokerageSubTyp.equals("Derivatives")) {
                                                if (object3.getString("BrokerageSubTypeCategoryName").equals("Futures")) {
                                                    derivatives_futures_value = object3.getString("Brokerage");
                                                    unitDerivetivefuture_value = object3.getString("BrokerageSlot");
//                                                    unitDerivetivefuture.setText(unitDerivetivefuture_value);

//                                                    if (unitDerivetivefuture_value.equals("Percentage")) {
//                                                        unitDerivetivefuture.setText("%");
//                                                    } else {
//                                                        unitDerivetivefuture.setText(unitDerivetivefuture_value);
//                                                    }
                                                    unitDerivetivefuture.setText(unitDerivetivefuture_value);

                                                } else if (object3.getString("BrokerageSubTypeCategoryName").equals("Options")) {
                                                    derivatives_options_value = object3.getString("Brokerage");
                                                    unitDerivetiveoption_value = object3.getString("BrokerageSlot");
//
                                                    unitDerivativeOption.setText(unitDerivetiveoption_value);
                                                } else {

                                                }
                                            } else if (BrokerageSubTyp.equals("Currency Derivatives")) {
                                                if (object3.getString("BrokerageSubTypeCategoryName").equals("Futures")) {
                                                    currency_derivatives_futures_value = object3.getString("Brokerage");
                                                    String unitCurrencyfuture_value = object3.getString("BrokerageSlot");
//                                                    unitCurrencyfuture.setText(unitCurrencyfuture_value);


//                                                    if (unitCurrencyfuture_value.equals("Percentage")) {
//                                                        unitCurrencyfuture.setText("%");
//                                                    } else {
//                                                        unitCurrencyfuture.setText(unitDerivetiveoption_value);
//                                                    }
                                                    unitCurrencyfuture.setText(unitCurrencyfuture_value);

                                                } else if (object3.getString("BrokerageSubTypeCategoryName").equals("Options")) {
                                                    currency_derivatives_options_value = object3.getString("Brokerage");

                                                    String unitCurrencyoption_value = object3.getString("BrokerageSlot");
//                                                    unitCurrencyoption.setText(unitCurrencyoption_value);


//                                                    if (unitCurrencyoption_value.equals("Percentage")) {
//                                                        unitCurrencyoption.setText("%");
//                                                    } else {
//                                                        unitCurrencyoption.setText(unitCurrencyoption_value);
//
//                                                    }
                                                    unitCurrencyoption.setText(unitCurrencyoption_value);
                                                } else {

                                                }
                                            } else if (BrokerageSubTyp.equals("Commodity Derivatives")) {
                                                if (object3.getString("BrokerageSubTypeCategoryName").equals("Futures")) {
                                                    commodity_derivatives_futures_value = object3.getString("Brokerage");
                                                    String unitCommodityfuture_value = object3.getString("BrokerageSlot");

//                                                    if (unitCommodityfuture_value.equals("Percentage")) {
//                                                        unitCommodityfuture.setText("%");
//                                                    } else {
//                                                        unitCommodityfuture.setText(unitCommodityfuture_value);
//                                                    }
                                                    unitCommodityfuture.setText(unitCommodityfuture_value);
                                                } else if (object3.getString("BrokerageSubTypeCategoryName").equals("Options")) {
                                                    commodity_derivatives_options_value = object3.getString("Brokerage");
                                                    String unitCommodityoption_value = object3.getString("BrokerageSlot");
//                                                    if (unitCommodityoption_value.equals("Percentage")) {
//                                                        unitCommodityoption.setText("%");
//                                                    } else {
//                                                        unitCommodityoption.setText(unitCommodityoption_value);
//                                                    }
                                                    unitCommodityoption.setText(unitCommodityoption_value);
                                                } else {
                                                } } } }
                                }

                                System.out.println("Data1" + commodity_derivatives_futures_value);
                                System.out.println("Data2" + commodity_derivatives_options_value);
                                System.out.println("Data3" + currency_derivatives_futures_value);
                                System.out.println("Data4" + currency_derivatives_options_value);
                                System.out.println("Data5" + derivatives_futures_value);
                                System.out.println("Data6" + derivatives_options_value);
                                System.out.println("Data7" + stock_delivery_value);
                                System.out.println("Data8" + stock_intraday_value);

                                commodity_derivatives_futures.setText(commodity_derivatives_futures_value);
                                commodity_derivatives_options.setText(commodity_derivatives_options_value);
                                currency_derivatives_futures.setText(currency_derivatives_futures_value);
                                currency_derivatives_options.setText(currency_derivatives_options_value);
                                derivatives_futures.setText(derivatives_futures_value);
                                derivatives_options.setText(derivatives_options_value);
                                stock_delivery.setText(stock_delivery_value);
                                stock_intraday.setText(stock_intraday_value);
                                turnOverEdit.setText("");
                            }
                        }


                        brokerageAlreadyFound = true;


                        if (isLessThanRequired()) {

                            uploadAdditionalDocLayout.setVisibility(View.VISIBLE);

                        }else{

                            uploadAdditionalDocLayout.setVisibility(View.GONE);
                        }


                    } else {

                    }

                } catch (JSONException ex) {

                    System.out.println("getBrokerageByCompanyId" + "getBrokex" + ex.getMessage() + "");

                }

            } else if (Method.equals("addBrokerage")) {

                try {

                    JSONObject mainObj = new JSONObject(Data);
                    System.out.println("AddBrokerage_response" + mainObj.toString() + "");
                    if (mainObj.getString("status").equals("success")) {

                        if (acProgressFlower != null) {
                            if (acProgressFlower.isShowing()) {
                                acProgressFlower.dismiss();
                            }
                        }
                        Toast.makeText(getActivity(), "Brokerage save successfully", Toast.LENGTH_SHORT).show();
                        closeViewBrockrageDoc();
                    } else {
                        if (acProgressFlower != null) {
                            if (acProgressFlower.isShowing()) {
                                acProgressFlower.dismiss();
                            }
                        }
                        Toast.makeText(getActivity(), mainObj.getString("message") + "", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException ex) {

                    System.out.println("ErrorDigioJSON" + ex.getMessage());

                }

            }else if (Method.equals("PDFCompressionAuth")) {
                try{
                    JSONObject mainObj = new JSONObject(Data);
                    pdfCompressionTOKEN = mainObj.getString("token");
                    System.out.println("pdfCompressionTOKEN" + pdfCompressionTOKEN);
                    AndroidNetworking.get(WebServices.getPdfCompressionCompressURL)
                            .addHeaders("Authorization", "Bearer " + pdfCompressionTOKEN)
                            .setPriority(Priority.LOW)
                            .setOkHttpClient(UtilsClass.getSlrClubClient1(context))
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        server = response.getString("server");
                                        task = response.getString("task");

                                        final ProgressDialog pd = new ProgressDialog(getActivity());
                                        pd.setTitle("Uploading PDF ...");
                                        pd.show();

                                        AndroidNetworking.post("https://" + server + "/v1/upload")
                                                .addBodyParameter("task", task)
                                                .addBodyParameter("cloud_file", WebServices.getBaseURL + "/fileupload/downloadFile/" + AppInfo.ekycApplicationId + "/Equity_Form")
                                                .addHeaders("Authorization", "Bearer " + pdfCompressionTOKEN)
                                                .setTag("test")
                                                .setPriority(Priority.MEDIUM)
                                                .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                                                .build()
                                                .getAsJSONObject(new JSONObjectRequestListener() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        // do anything with response

                                                        if (pd.isShowing())
                                                            pd.dismiss();
                                                        try {
                                                            String server_filename = response.getString("server_filename");
                                                            System.out.println("Server_FileName" + server_filename);

                                                            JSONArray fileArr = new JSONArray();
                                                            JSONObject obj = new JSONObject();
                                                            obj.put("server_filename", server_filename);
                                                            obj.put("filename", AppInfo.ekycApplicationId + "_EquityForm.pdf");
                                                            fileArr.put(obj);

                                                            System.out.println("FileARRAY" + fileArr.toString());
                                                            AndroidNetworking.post("https://" + server + "/v1/process")
                                                                    .addBodyParameter("task", task)
                                                                    .addBodyParameter("files", fileArr.toString())
                                                                    .addHeaders("Authorization", "Bearer " + pdfCompressionTOKEN)
                                                                    .setTag("ol")
                                                                    .setPriority(Priority.MEDIUM)
                                                                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                                                                    .build()
                                                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                                                        @Override
                                                                        public void onResponse(JSONObject response) {
                                                                            // do anything with response
                                                                            System.out.println("ProcessResponse>>>" + response.toString());

                                                                        }

                                                                        @Override
                                                                        public void onError(ANError error) {
                                                                            // handle error
                                                                            System.out.println("ProcessResponse>>>" + error.getErrorBody());
                                                                        }
                                                                    });


                                                        }catch (JSONException e) {
                                                            e.printStackTrace();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        // handle error
                                                        System.out.println("error" + error.getErrorBody());
                                                        if (pd.isShowing())
                                                            pd.dismiss();
                                                    }
                                                });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });


                } catch (Exception ex) {
                    System.out.println("ErrorDigioJSON" + ex.getMessage());
                }
            } else if (Method.equals("ChangeEKycAppStatus")) {

                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Your Application is successfully submitted for review.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                Intent i = new Intent(getActivity(),
                                        MainHomeActivity.class);
                                i.putExtra("REDIRECT_POSITION", "");
                                i.putExtra("ITEM_POS", "0");
                                activity.finish();
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else if (Method.equals("GetSignSourceDetails")) {

                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }

                JSONObject mainObj = null;
                try {
                    mainObj = new JSONObject(Data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("REVIEW_AMAN>>>" + Data + "");
                System.out.println("Method_aman" + Method + "");
                String satus = null;
                try {
                    satus = mainObj.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (satus.equals("success")) {

                    try {
                        JSONObject jsonObject = mainObj.getJSONObject("data");
                        String eSignatureWeb = jsonObject.getString("eSignatureMobile");
                        // String eSignatureWeb = jsonObject.getString("eSignatureMobile");
                        if (eSignatureWeb.equals("digio")) {
                            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(WHITE)

                                    .fadeColor(DKGRAY).build();
                            acProgressFlower.show();

                            calluploadondigio();
                        } else if (eSignatureWeb.equals("nsdl")) {
                            Intent intu = new Intent(getActivity(), CustomEsignActivity.class);
                            intu.putExtra("eKYCApplivationId", AppInfo.ekycApplicationId);
                            startActivity(intu);
                        } else if (eSignatureWeb.equals("offline")) {
                            String eSignatureMessage = jsonObject.getString("eSignatureMessage");
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("WARNING !");
                            builder.setMessage(eSignatureMessage + "");
                            builder.setPositiveButton("GO TO OFFLINE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Aadhar is not linked with mobile
                                    isAadharLinkedOrNot = false;

//                setAaadharLinkedStatus(getActivity(), isAadharLinked);
                                    IsBoolean = true;

                                    review_applicationMainLayout.setVisibility(View.GONE);
                                    error_with_aadhar.setVisibility(View.GONE);
                                    congratulationsLayout.setVisibility(View.GONE);
                                    aadhar_not_linked.setVisibility(View.VISIBLE);
                                    //additional_doc.setVisibility(View.GONE);
                                    reviewWebView.setVisibility(View.GONE);
                                }
                            });
                            builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setCancelable(false);
                            builder.create().show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                        /*ShowMessage.showSuccessMessage(mainObj.getString("message"),
                                getActivity());*/
            } else if (Method.equals("fillEquityFormEMudra")) {

                try {
                    JSONObject mainObj = new JSONObject(Data);
                    if (mainObj.has("status")) {

                        if (mainObj.getString("status").equals("success")) {


                            String Equity_url = mainObj.getString("data");

                            // This code will execute if file will download
                            // From the server end
                            // without compression
                            downloadForEmudra(Equity_url);
                            emudraPdfPath = Equity_url;

                        }
                    }

                } catch (JSONException ex) {


                }


            } else if (Method.equals("ESIGN_FOR_EMUDRA")) {
                try {

                    JSONObject response = new JSONObject(Data);
                    Log.d("ESIGN_FOR_EMUDRA", response.toString() + "------");
                    JSONObject data = response.getJSONObject("data");
                    if (data.getBoolean("ReqStatus")) {
                        System.out.println("ESIGN_FOR_EMUDRA" + data.toString() + "----");

                        String HTTPSPostURL = data.getString("HTTPSPostURL");
                        String Parameter1 = data.getString("Parameter1").toString().trim();
                        String Parameter2 = data.getString("Parameter2").toString().trim();
                        String Parameter3 = data.getString("Parameter3").toString().trim();
                        Intent i = new Intent(getActivity(), EmudraActivity.class);

                        i.putExtra("EkycId", AppInfo.ekycApplicationId);
//                        i.putExtra("Parameter1",Parameter1);
//                        i.putExtra("Parameter2",Parameter2);
//                        i.putExtra("Parameter3",Parameter3);
                        startActivity(i);

                    } else {
// "Parameter3": "Kh1jxvuvhEY0HTGnmWjrCstFwz9MhMOauFJsOvezgyg2Y+G4B5UkBao37kDcnnON",
//        "HTTPSPostURL": "https://testgateway.emsigner.com/eMsecure/V3_0/Index"
                        Toast.makeText(getActivity(),
                                data.getString("Message"), Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException ex) {

                    System.out.println("ErrorDigioJSON" + ex.getMessage());

                }

            } else if (Method.equals("IsClientCodeExisted")) {

                try {

                    JSONObject response = new JSONObject(Data);
                    System.out.println("IsClientCodeExisted" + response.toString() + "------");

                    String status = response.getString("status");
                    System.out.println("status" + status.toString() + "------");

                    String message = response.getString("message");
                    System.out.println("message" + message.toString() + "------");

                    JSONObject data = response.getJSONObject("data");
                    System.out.println("data" + data + "------");


                    if (data.getBoolean("ResponseStatus")) {
                        Toast.makeText(getActivity(),
                                data.getString("ResponseMessage"), Toast.LENGTH_LONG).show();


                        parameters = new HashMap<String, Object>();
                        parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                        getRes = new getResponse(getActivity());

                        getRes.getResponseFromURL(WebServices.getBaseURL +
                                        WebServices.EkycReviewDetails,
                                "EkycReviewDetails",
                                parameters);

                    } else {
                           /* Toast.makeText(getActivity(),
                                    data.getString("ResponseMessage"), Toast.LENGTH_LONG).show();
*/
                        ShowMessage.showErrorMessage(data.getString("ResponseMessage"),
                                getActivity());
                    }
                } catch (JSONException ex) {

                    System.out.println("ErrorDigioJSON" + ex.getMessage());

                }

            }
        }
    };

    public String formatDate(String dateToFormat, String inputFormat, String outputFormat) {
        try {
            Log.e("DATE", "Input Date Date is " + dateToFormat);

            String convertedDate = new SimpleDateFormat(outputFormat)
                    .format(new SimpleDateFormat(inputFormat)
                            .parse(dateToFormat));

            Log.e("DATE", "Output Date is " + convertedDate);

            //Update Date
            return convertedDate;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ReviewFragment() {
        // Required empty public constructor
    }

    public void savecliencode() {

        getRes.getResponse(WebServices.getBaseURL +
                        WebServices.IsClientCodeExisted + "?ClientCode=" + UserPrefix + userClientCode_edtitext.getText().toString() + "&ClientId=" + AppInfo.CUSTOMERID,
                "IsClientCodeExisted",
                parameters);

    }

    public static void GetDocumentsByEkycId(Context context) {
        parameters = new HashMap<String, Object>();
        parameters.put("EkycId", AppInfo.ekycApplicationId);
        getRes = new getResponse(context);

        getRes.getResponseFromURL(WebServices.getBaseURL +
                        WebServices.GetDocumentsByEkycId,
                "GetDocumentsByEkycId",
                parameters);
    }

    public static void checkAndUpdateExistingValues(Context con) {
        Log.d("YeValiApi", "YahaCla2222");
        closeAdditionalDoc();
        closeAddNominees();
        closeViewBrockrageDoc();
        NewEntryFragment.step4Line.setBackgroundColor(Color.parseColor("#19e9b5"));

        final getResponse getRes = new getResponse(con);

//        getRes.getResponse(WebServices.getBaseURL +
//                        WebServices.GetLoanDetailsByEkycId + "?EkycId=" + eKycId,
//                "GetLoanDetails",
//                null);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                System.out.println("Activity" + "CreatedWithCallvalue" + "  " + AppInfo.ekycApplicationId);
                try {

                    HashMap<String, Object> parameters;
                    parameters = new HashMap<String, Object>();
                    parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);

                    getRes.getResponseFromURL(WebServices.getBaseURL +
                                    WebServices.EkycReviewDetails,
                            "EkycReviewDetails",
                            parameters);


                } catch (Exception ex) {
                    System.out.println("ExceptionOnCall" + ex.getMessage());
                }
            }
        }, 500);


    }

    public static void closeAdditionalDoc() {
        if (bottomSheetBehaviorAdditionDoc != null) {
            if (bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }
    }

    public static boolean isAdditionalDocOpened() {
        return bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }

    public static void closeAddNominee() {
        if (bottomSheetBehaviorAddNominee != null) {
            if (bottomSheetBehaviorAddNominee.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehaviorAddNominee.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }
    }

    public static void closeAddNominees() {
        if (bottomSheetBehaviorAddNominees != null) {
            if (bottomSheetBehaviorAddNominees.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }
    }

    public static boolean isNomineeDocOpened() {
        return bottomSheetBehaviorAddNominees.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }

    public static boolean isAddNomineeOpened() {
        return bottomSheetBehaviorAddNominee.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }


    public static void closeViewBrockrageDoc() {
        if (bottomSheetBehaviorBrockrage != null) {
            if (bottomSheetBehaviorBrockrage.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }
    }

    public static boolean isViewBrockrageOpened() {
        return bottomSheetBehaviorBrockrage.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }

    public static boolean isPhysicallDocOpen() {
        return IsBoolean;
    }

    public static void ClosePhysicalDoc() {
        review_applicationMainLayout.setVisibility(View.VISIBLE);
        error_with_aadhar.setVisibility(View.GONE);
        congratulationsLayout.setVisibility(View.GONE);
        aadhar_not_linked.setVisibility(View.GONE);
        //additional_doc.setVisibility(View.GONE);
        reviewWebView.setVisibility(View.GONE);
        IsBoolean = false;
    }

    public static void hideKeyboardReview(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private int getFileSize(URL url) {
        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(getSwastikaSSLSocketFactory());
            if (conn instanceof HttpsURLConnection) {
                conn.setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return 0;
        } catch (javax.security.cert.CertificateException e) {
            return 0;
        } catch (NoSuchAlgorithmException e) {
            return 0;
        } catch (NoSuchProviderException e) {
            return 0;
        } catch (CertificateException e) {
            return 0;
        } catch (KeyStoreException e) {
            return 0;
        } catch (KeyManagementException e) {
            return 0;
        }
    }

    private void saveDocumentsData(
            JSONArray document,
            boolean esigned,
            String MICR,
            String bankBranch,
            String bankCity,
            String bankState,
            String bankZipCode,
            String country,
            String AccountNumber,
            String AccountHolderName,
            String IfscCode,
            String BankName) {


        String aadharBackImageUrl = null;
        String aadharFrontImageUrl = null;
        String addressForntUrl = null;
        String addressBackUrl = null;
        String panCardImageUrl = null;
        String bankStmtUrl = null;

        String itrDocument = null;
        String itrDocumentCancelled = null;
        String incomeProof = null;
        String salarySlipUrl = null;
        String salarySlipCancelledUrl = null;

        String userPhotoUrl = null;
        String userSignatureUrl = "";
        String varificationPhotoUrl = null;

        String equityDoc1Url = "";
        String equityDoc2Url = "";
        String equityDoc3Url = "";
        String equityDoc4Url = "";
        String equityDoc5Url = "";
        String equityDoc6Url = "";
        String equityDoc7Url = "";
        String equityDoc8Url = "";
        String equityDoc9Url = "";
        String equityDoc10Url = "";
        String equityDoc11Url = "";
        String equityDoc12Url = "";
        String equityDoc13Url = "";
        String equityDoc14Url = "";
        String equityDoc15Url = "";
        String equityDoc16Url = "";
        String equityDoc17Url = "";
        String equityDoc18Url = "";
        String equityDoc19Url = "";
        String equityDoc20Url = "";


        String comodityDoc1Url = "";
        String comodityDoc2Url = "";
        String comodityDoc3Url = "";
        String comodityDoc4Url = "";
        String comodityDoc5Url = "";
        String comodityDoc6Url = "";
        String comodityDoc7Url = "";
        String comodityDoc8Url = "";
        String comodityDoc9Url = "";
        String comodityDoc10Url = "";
        String comodityDoc11Url = "";
        String comodityDoc12Url = "";
        String comodityDoc13Url = "";
        String comodityDoc14Url = "";
        String comodityDoc15Url = "";
        String comodityDoc16Url = "";
        String comodityDoc17Url = "";
        String comodityDoc18Url = "";
        String comodityDoc19Url = "";
        String comodityDoc20Url = "";

        String bankStatement_Url_PhyicalDoc = "";
        String itr_Url_PhyicalDoc = "";
        String salarySlip_Url_PhyicalDoc = "";
        String cancelledCheque_Url_PhyicalDoc = "";


        String panCard_Url_PhyicalDoc = "";
        String aadharBack_Url_PhyicalDoc = "";
        String aadharFront_Url_PhyicalDoc = "";

        String proof_of_dispatch = "";
        String AdditionalDocument1 = "";
        String AdditionalDocument2 = "";
        String additionalDescription1 = "";
        String additionalDescription2 = "";
        String OtherAddressProof = "";
        String OtherAddressProofType = "Aadhar Card";
        String VerificationVideo = "";

        try {


            for (int i = 0; i < document.length(); i++) {

                JSONObject docObj = document.getJSONObject(i);


                if (docObj.getString("documentType").equals("Aadhar_Back")) {

                    aadharBackImageUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Aadhar_Front")) {

                    aadharFrontImageUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Address_Front")) {

                    addressForntUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Address_Back")) {

                    addressBackUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Pan")) {

                    panCardImageUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Bank_Statement")) {

                    bankStmtUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Photo")) {

                    userPhotoUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Signature")) {

                    userSignatureUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("ITR")) {

                    itrDocument = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Salary_Slip")) {

                    salarySlipUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Cancelled_Cheque")) {

                    salarySlipCancelledUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                    itrDocumentCancelled = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Income")) {

                    incomeProof = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Cancelled_Cheque")) {

                    salarySlipCancelledUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                    itrDocumentCancelled = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("PhotoVerification")) {

                    varificationPhotoUrl = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                    // Toast.makeText(getActivity(), "Verification Found", Toast.LENGTH_LONG).show();
                } else if (docObj.getString("documentType").equals("Equity_Page_1")) {

                    equityDoc1Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Equity_Page_2")) {

                    equityDoc2Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_3")) {

                    equityDoc3Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_4")) {

                    equityDoc4Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Equity_Page_5")) {

                    equityDoc5Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Equity_Page_6")) {

                    equityDoc6Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_7")) {

                    equityDoc7Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_8")) {

                    equityDoc8Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_9")) {

                    equityDoc9Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_10")) {

                    equityDoc10Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_11")) {

                    equityDoc11Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_12")) {

                    equityDoc12Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_13")) {

                    equityDoc13Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_14")) {

                    equityDoc14Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_15")) {

                    equityDoc15Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_16")) {

                    equityDoc16Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_17")) {

                    equityDoc17Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_18")) {

                    equityDoc18Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_19")) {

                    equityDoc19Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Page_20")) {

                    equityDoc20Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_1")) {

                    comodityDoc1Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_2")) {

                    comodityDoc2Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_3")) {

                    comodityDoc3Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_4")) {

                    comodityDoc4Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_5")) {

                    comodityDoc5Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_6")) {

                    comodityDoc6Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_7")) {

                    comodityDoc7Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_8")) {

                    comodityDoc8Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_9")) {

                    comodityDoc9Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_10")) {

                    comodityDoc10Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_11")) {

                    comodityDoc11Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_12")) {

                    comodityDoc12Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_13")) {

                    comodityDoc13Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_14")) {

                    comodityDoc14Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_15")) {

                    comodityDoc15Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_16")) {

                    comodityDoc16Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_17")) {

                    comodityDoc17Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_18")) {

                    comodityDoc18Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_19")) {

                    comodityDoc19Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Commodity_Page_20")) {

                    comodityDoc20Url = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Pan")) {

                    panCard_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Aadhar_Front")) {

                    aadharFront_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Aadhar_Back")) {

                    aadharBack_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Bank_Statement")) {

                    bankStatement_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Bank_Statement")) {

                    bankStatement_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Salary_Slip")) {

                    salarySlip_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_ITR")) {

                    itr_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("Equity_Signature")) {

                    cancelledCheque_Url_PhyicalDoc = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                } else if (docObj.getString("documentType").equals("Proof_Of_Dispatch")) {

                    proof_of_dispatch = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                } else if (docObj.getString("documentType").equals("AdditionalDocument1")) {

                    AdditionalDocument1 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                    additionalDescription1 = docObj.optString("additionalDescription");

                } else if (docObj.getString("documentType").equals("AdditionalDocument2")) {

                    AdditionalDocument2 = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");
                    additionalDescription2 = docObj.optString("additionalDescription");
                } else if (docObj.getString("documentType").equals("OtherAddressProof")) {

                    OtherAddressProof = docObj.optString("documentBaseUrl") + docObj.optString("documentUrl");
                    OtherAddressProofType = docObj.optString("additionalDescription");

                } else if (docObj.getString("documentType").equals("VerificationVideo")) {

                    VerificationVideo = docObj.getString("documentBaseUrl") + docObj.getString("documentUrl");

                }
            }
            userDocuments userDoc = new userDocuments(aadharBackImageUrl,
                    "" + aadharFrontImageUrl,
                    "" + addressForntUrl,
                    "" + addressBackUrl,
                    "" + panCardImageUrl,
                    "" + bankStmtUrl,
                    "" + itrDocument,
                    "" + itrDocumentCancelled,
                    "" + incomeProof,

                    "" + userPhotoUrl,
                    "" + userSignatureUrl,

                    "" + salarySlipUrl,
                    "" + salarySlipCancelledUrl,
                    "" + varificationPhotoUrl,
                    "" + equityDoc1Url,
                    "" + equityDoc2Url,
                    "" + equityDoc3Url,
                    "" + equityDoc4Url,
                    "" + equityDoc5Url,
                    "" + equityDoc6Url,
                    "" + equityDoc7Url,
                    "" + equityDoc8Url,
                    "" + equityDoc9Url,
                    "" + equityDoc10Url,
                    "" + equityDoc11Url,
                    "" + equityDoc12Url,
                    "" + equityDoc13Url,
                    "" + equityDoc14Url,
                    "" + equityDoc15Url,
                    "" + equityDoc16Url,
                    "" + equityDoc17Url,
                    "" + equityDoc18Url,
                    "" + equityDoc19Url,
                    "" + equityDoc20Url,
                    "" + comodityDoc1Url,
                    "" + comodityDoc2Url,
                    "" + comodityDoc3Url,
                    "" + comodityDoc4Url,
                    "" + comodityDoc5Url,
                    "" + comodityDoc6Url,
                    "" + comodityDoc7Url,
                    "" + comodityDoc8Url,
                    "" + comodityDoc9Url,
                    "" + comodityDoc10Url,
                    "" + comodityDoc11Url,
                    "" + comodityDoc12Url,
                    "" + comodityDoc13Url,
                    "" + comodityDoc14Url,
                    "" + comodityDoc15Url,
                    "" + comodityDoc16Url,
                    "" + comodityDoc17Url,
                    "" + comodityDoc18Url,
                    "" + comodityDoc19Url,
                    "" + comodityDoc20Url,
                    "" + panCard_Url_PhyicalDoc,
                    "" + aadharBack_Url_PhyicalDoc,
                    "" + aadharFront_Url_PhyicalDoc,
                    "" + bankStatement_Url_PhyicalDoc,
                    "" + itr_Url_PhyicalDoc,
                    "" + salarySlip_Url_PhyicalDoc,
                    "" + cancelledCheque_Url_PhyicalDoc,
                    "" + proof_of_dispatch, AccountNumber, AccountHolderName
                    , IfscCode, BankName, esigned, AdditionalDocument1, AdditionalDocument2,
                    additionalDescription1, additionalDescription2, OtherAddressProof, OtherAddressProofType, MICR, VerificationVideo, bankBranch, bankCity, bankState, bankZipCode, country);

            FinalApplicationForm.setUserDocuments(userDoc);
        } catch (JSONException s) {
            s.getMessage();
        }
    }

    private void callAddtionalData() {


        System.out.println("DurgeshChoudhary"+ AppInfo.ekycApplicationId);

       /* HashMap<String, Object> parameters;
        parameters = new HashMap<String, Object>();
        parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);*/


        String URL_Data=WebServices.getBaseURL+WebServices.getAllAdditionalDetails + "?ekycApplicationId=" + AppInfo.ekycApplicationId;
        System.out.println("GETNOMINEE_URL"+ URL_Data);

        //https://stagingekycapi.swastika.co.in/apiAdditionalDetails/GetAllAdditionalDetails_New?ekycApplicationId=81313
        //https://stagingekycapi.swastika.co.in/api/AdditionalDetails/GetAllAdditionalDetails_New?ekycApplicationId=81313

        getRes.getResponse(WebServices.getBaseURL + WebServices.getAllAdditionalDetails + "?ekycApplicationId=" + AppInfo.ekycApplicationId,
                "callAddtionalData",
                null);
        System.out.println("DurgeshChoudharyparameters"+ parameters);
    }

    private void callNewDigio(String document_id) {

        if (acProgressFlower != null) {
            if (acProgressFlower.isShowing()) {
                acProgressFlower.dismiss();
            }
        }
        try {
            Digio digio = new Digio();
            DigioConfig digioConfig = new DigioConfig();
            digioConfig.setLogo(" "); //Your company logo
            digioConfig.setEnvironment(DigioEnvironment.PRODUCTION);
//                    digioConfi//Stage is sandbox
            digioConfig.setServiceMode(DigioServiceMode.OTP);//FP is fingerprint
            digioConfig.setAadhaarId(ScanResultAadhar.uid);


            try {

                digio.init(getActivity(), digioConfig);
                digioDownloaded = false;
                UserIdentity user = FinalApplicationForm.getUserIdentyObject();
                System.out.println("UploadOnDigio_url12" + ">>>>" + MainEmail + "");
                digioDocumentId = document_id;
                AppInfo.digioDocumentId = document_id;
                digio.esign(document_id, MainEmail);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {

            Toast.makeText(getActivity(), ex.getMessage() + "", Toast.LENGTH_LONG).show();
            System.out.println("ErrorDigioJSON" + ex.getMessage());

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review_application_layout, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        documentUploaded = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        if(AppInfo.CustomerName != null || !(AppInfo.CustomerName.equalsIgnoreCase(""))) {
            filledFormFileName = AppInfo.CustomerName + "_" + currentDateandTime + ".pdf";
        }else{
            filledFormFileName = currentDateandTime + ".pdf";
        }

        File_download_dialog = new ProgressDialog(getActivity());
        tempHoldImageView = new ImageView(getActivity());
        AdditionalObj = new JSONObject();
        File_download_dialog = new ProgressDialog(getActivity());
        minorToggle = getActivity().findViewById(R.id.minorToggle);
        minorToggle_new = getActivity().findViewById(R.id.minorToggle_new);
        minorToggle_news = getActivity().findViewById(R.id.minorToggle_news);
        minorToggle_news.setChecked(false);
        nominationsLayout_new = getActivity().findViewById(R.id.nominationsLayout_new);
        uploadAdditionalDocLayout = getActivity().findViewById(R.id.uploadAdditionalDocLayout);
        ll_reviewSubmit = getActivity().findViewById(R.id.ll_reviewSubmit);
        llVarification = getActivity().findViewById(R.id.llVarification);
        add_remove_layout = getActivity().findViewById(R.id.add_remove_layout);
        uploadAdditionalDocLayout.setVisibility(View.GONE);
        rl_uploadBrokrage = getActivity().findViewById(R.id.rl_uploadBrokrage);
        notelayout = getActivity().findViewById(R.id.notelayout);
        stock_delivery = getActivity().findViewById(R.id.stock_delivery_bottom);
        stock_intraday = getActivity().findViewById(R.id.stock_intraday_bottom);
        derivatives_futures = getActivity().findViewById(R.id.derivatives_futures_bottom);
        derivatives_options = getActivity().findViewById(R.id.derivatives_options_bottom);
        currency_derivatives_futures = getActivity().findViewById(R.id.currency_derivatives_futures_bottom);
        currency_derivatives_options = getActivity().findViewById(R.id.currency_derivatives_options_bottom);
        commodity_derivatives_futures = getActivity().findViewById(R.id.commodity_derivatives_futures_bottom);
        commodity_derivatives_options = getActivity().findViewById(R.id.commodity_derivatives_options_bottom);
        rl_addnominelayout=getActivity().findViewById(R.id.rl_addnominelayout);

        ll_nominesecondlayout=getActivity().findViewById(R.id.ll_nominesecondlayout);
        ll_nominethirdlayout=getActivity().findViewById(R.id.ll_nominethirdlayout);
        toggle_button=getActivity().findViewById(R.id.toggle_button);
        cancelnomineelayout1=getActivity().findViewById(R.id.cancelnomineelayout1);
        cancelnomineelayout2=getActivity().findViewById(R.id.cancelnomineelayout2);
        cancelnomineelayout3=getActivity().findViewById(R.id.cancelnomineelayout3);
        testImageIV = getActivity().findViewById(R.id.testImageIV);


        cancelnomineelayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isnomineereadable1){
                    String nomineid=nomineeId1;
                    deleta_data(nomineid);
                    edt_firstnominee.setText("");
                    edtpercentagesharing.setText("");
                    DOB1="";
                    dobofnominee1.setText("");
                    relationshipapplicant.setText("");
                    addone.setText("");
                    addtwo.setText("");
                    addthree.setText("");
                    addstate.setText("");
                    addcity.setText("");
                    addpincode.setText("");
                    document_typeNominee_1.setText("");
                    edt_gurdiannameone.setText("");
                    edt_gurdianaddress_one.setText("");
                    edt_gurdianaddress_two.setText("");
                    edt_gurdianaddress_three.setText("");
                    txt_gurdian_city.setText("");
                    relationshipapplicant_guardian.setText("");
                    editgurdianpincode.setText("");
                    document_typeNomineeGurdian_1.setText("");
                    nominee_oneImageview.setImageResource(R.drawable.button_border);
                    gurdian_imageview.setImageResource(R.drawable.button_border);
                }else{
                        edt_firstnominee.setText("");
                        edtpercentagesharing.setText("");
                        DOB1="";
                        dobofnominee1.setText("");
                        relationshipapplicant.setText("");
                        addone.setText("");
                        addtwo.setText("");
                        addthree.setText("");
                        addstate.setText("");
                        addcity.setText("");
                        addpincode.setText("");
                        document_typeNominee_1.setText("");
                        edt_gurdiannameone.setText("");
                            edt_gurdianaddress_one.setText("");
                            edt_gurdianaddress_two.setText("");
                            edt_gurdianaddress_three.setText("");
                            txt_gurdian_city.setText("");
                            relationshipapplicant_guardian.setText("");
                            editgurdianpincode.setText("");
                            document_typeNomineeGurdian_1.setText("");
                            nominee_oneImageview.setImageResource(R.drawable.button_border);
                            gurdian_imageview.setImageResource(R.drawable.button_border);
                            }}}
                        );
        cancelnomineelayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_nominesecondlayout.setVisibility(View.GONE);
                checkcount=false;
                secondnomineactive=false;
                if(isnomineereadable2){
                    String nomineid=nomineeId2;
                    deleta_data(nomineid);
                    edt_name_of_nominee2.setText("");
                    edtpercentagesharing2.setText("");
                    DOB2="";
                    dobofnominee2.setText("");
                    relationshipapplicant2.setText("");
                    addres_one_of_nominee2.setText("");
                    addres_two_of_nominee2.setText("");
                    addres_three_of_nominee2.setText("");
                    addstate2.setText("");
                    addcity2.setText("");
                    addpincode2.setText("");
                    document_typeNominee_2.setText("");
                    edt_gurdianname2.setText("");
                    edt_gurdianaddress2.setText("");
                    edt_gurdianaddress_two2.setText("");
                    edt_gurdianaddress_three2.setText("");
                    txt_gurdian_city2.setText("");
                    relationshipapplicant_guardian2.setText("");
                    edittextpincode2.setText("");
                    document_typeNomineeGurdian_2.setText("");
                    second_nominee_image.setImageResource(R.drawable.button_border);
                    gurdian_Second_imageview.setImageResource(R.drawable.button_border);

                }else{
                    edt_name_of_nominee2.setText("");
                    edtpercentagesharing2.setText("");
                    DOB2="";
                    dobofnominee2.setText("");
                    relationshipapplicant2.setText("");
                    addres_one_of_nominee2.setText("");
                    addres_two_of_nominee2.setText("");
                    addres_three_of_nominee2.setText("");
                    addstate2.setText("");
                    addcity2.setText("");
                        addpincode2.setText("");
                        document_typeNominee_2.setText("");
                        edt_gurdianname2.setText("");
                        edt_gurdianaddress2.setText("");
                        edt_gurdianaddress_two2.setText("");
                        edt_gurdianaddress_three2.setText("");
                        txt_gurdian_city2.setText("");
                        relationshipapplicant_guardian2.setText("");
                        edittextpincode2.setText("");
                        document_typeNomineeGurdian_2.setText("");
                    second_nominee_image.setImageResource(R.drawable.button_border);
                    gurdian_Second_imageview.setImageResource(R.drawable.button_border);
                   }}
                 });
        cancelnomineelayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_nominethirdlayout.setVisibility(View.GONE);
                checkcount=true;
                if(isnomineereadable3){
                    String nomineid=nomineeId3;
                    deleta_data(nomineid);
                    edt_nameofnominee3.setText("");
                    edtpercentagesharing3.setText("");
                    dobofnominee3.setText("");
                    relationshipapplicant3.setText("");
                    addone3.setText("");
                    addtwo3.setText("");
                    addthree3.setText("");
                    addstate3.setText("");
                    addcity3.setText("");
                    addpincode3.setText("");
                    document_typeNominee_3.setText("");
                    edt_gurdianname3.setText("");
                    edt_gurdianaddress_one3.setText("");
                    edt_gurdianaddress_two3.setText("");
                    edt_gurdianaddress_three3.setText("");
                    txt_gurdian_city3.setText("");
                    relationshipapplicant_guardian3.setText("");
                    edittextpincode3.setText("");
                    document_typeNomineeGurdian_3.setText("");
                    Third_nominee_image.setImageResource(R.drawable.button_border);
                    gurdian_imageviewthird.setImageResource(R.drawable.button_border);
                }else {
                    edt_nameofnominee3.setText("");
                    edtpercentagesharing3.setText("");
                    dobofnominee3.setText("");
                    relationshipapplicant3.setText("");
                    addone3.setText("");
                    addtwo3.setText("");
                    addthree3.setText("");
                    addstate3.setText("");
                    addcity3.setText("");
                    addpincode3.setText("");
                    document_typeNominee_3.setText("");
                    edt_gurdianname3.setText("");
                        edt_gurdianaddress_one3.setText("");
                        edt_gurdianaddress_two3.setText("");
                        edt_gurdianaddress_three3.setText("");
                        txt_gurdian_city3.setText("");
                        relationshipapplicant_guardian3.setText("");
                        edittextpincode3.setText("");
                        document_typeNomineeGurdian_3.setText("");
                       Third_nominee_image.setImageResource(R.drawable.button_border);
                       gurdian_imageviewthird.setImageResource(R.drawable.button_border);
                }}
             });

        rl_addnominelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllFieldsChecked){
                    if(checkcount)
                    {
                        String percentage=edtpercentagesharing.getText().toString();
                        double isNum = Double.parseDouble(percentage);
                        if(isNum==100.00||isNum==100){
                            Toast.makeText(getActivity(),"No more percentage to share", Toast.LENGTH_LONG).show();
                            secondnomineactive=false;

                        }else{
                            if(nomineeImage_first||exist_nomineimage1 && isNum<100 &&!isminornominee){
                                ll_nominesecondlayout.setVisibility(View.VISIBLE);
                                checkcount=false;
                                secondnomineactive=true;
                            }else{

                                if(isGurdianValidation &&isNum<100&&nomineeGurdianImage_first){
                                    ll_nominesecondlayout.setVisibility(View.VISIBLE);
                                    checkcount=false;
                                    secondnomineactive=true;
                                }else{
                                    if(nomineeGurdianImage_first&&isNum<100&&nomineeImage_first){
                                        ll_nominesecondlayout.setVisibility(View.VISIBLE);
                                        checkcount=false;
                                        secondnomineactive=true;
                                     }else{
                                        Toast.makeText(getActivity(),"Please Fill Nominee Form First 2", Toast.LENGTH_LONG).show();
                                    }}
                            } }}
                    else
                    {
                        String percentage=edtpercentagesharing.getText().toString();
                        double isNum = Double.parseDouble(percentage);

                        if(isNum==100.00||isNum==100){
                            Toast.makeText(getActivity(),"No more percentage to share", Toast.LENGTH_LONG).show();
                            secondnomineactive=false;
                        }else{
                            //secondnomineactive
                            if(isAllFieldsChecked&&!secondnomineactive){
                                ll_nominesecondlayout.setVisibility(View.VISIBLE);
                                checkcount=false;

                            }else if(isAllFieldsChecked&&secondnomineactive){
                                //isAllFieldsChecked_of_secondNominee=CheckAllFields_of_nomineesecond();
                                if(isAllFieldsChecked_of_secondNominee&&nomineeImage_second){
                                    double nomineoneshare_percent,nomineonetwoshare_percent;
                                    nomineoneshare_percent=Double.parseDouble(edtpercentagesharing.getText().toString());
                                    nomineonetwoshare_percent=Double.parseDouble(edtpercentagesharing2.getText().toString());
                                    double calculate=nomineoneshare_percent+nomineonetwoshare_percent;

                                    if(isAllFieldsChecked_of_secondNominee&&nomineeImage_second&&calculate==100.00){
                                        Toast.makeText(getActivity(),"No more Percentage to share", Toast.LENGTH_LONG).show();
                                    }else if(isAllFieldsChecked_of_secondNominee) {
                                        if (nomineeImage_second && calculate > 100.00) {
                                            Toast.makeText(getActivity(),"Sum of percentage sharing should be 100%", Toast.LENGTH_LONG).show();
                                        }else{

                                            ll_nominethirdlayout.setVisibility(View.VISIBLE);
                                            checkcount = true;
                                            thirdnomineactive=true;
                                        }
                                    } else {
                                        ll_nominethirdlayout.setVisibility(View.VISIBLE);
                                        checkcount = true;
                                        thirdnomineactive=true;
                                    }

                                }else if(isAllFieldsChecked_of_secondNominee&&!nomineeImage_second){
                                    Toast.makeText(getActivity(),"Please Fill Nominee Form Second", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getActivity(),"Please Fill Nominee Form Second", Toast.LENGTH_LONG).show();
                                }}
                            else{
                                Toast.makeText(getActivity(),"Please Fill Nominee Form Second", Toast.LENGTH_LONG).show();
                            } } }
                }else{
                    //Toast.makeText(getActivity(),"value=>"+value, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(),"data"+isAllFieldsChecked, Toast.LENGTH_LONG).show();
                    String percentage=edtpercentagesharing.getText().toString();
                    double isNum;
                    if(percentage.equals("")){
                        isNum=0.0;
                    }else {
                        isNum = Double.parseDouble(percentage);
                    }
                    if(isAllFieldsChecked&&value==1&&isNum==100){
                        Toast.makeText(getActivity(),"No more percentage to share", Toast.LENGTH_LONG).show();

                    }else if(isAllFieldsChecked&&value==1&&isNum<100){
                        ll_nominesecondlayout.setVisibility(View.VISIBLE);
                        checkcount=false;
                        secondnomineactive=true;
                    }else {
                        Toast.makeText(getActivity(),"Please Fill Nominee Form First 1 ", Toast.LENGTH_LONG).show();
                    }
                }}
              });
        unitStockDelivery = getActivity().findViewById(R.id.unitStockDelivery);
        checkBoxaddress = getActivity().findViewById(R.id.checkBoxaddress);
        checkBoxaddress_2 = getActivity().findViewById(R.id.checkBoxaddress_2);
        checkBoxaddress3 = getActivity().findViewById(R.id.checkBoxaddress3);
        checkBoxgurdianaddress3=getActivity().findViewById(R.id.checkBoxgurdianaddress3);
        gurdianaddresscheckbox=getActivity().findViewById(R.id.gurdianaddresscheckbox);
        gurdianaddresscheckbox_ofsecondnominee=getActivity().findViewById(R.id.gurdianaddresscheckbox_ofsecondnominee);
        unitDerivetivefuture = getActivity().findViewById(R.id.edtDerivetivefuture);
        unitDerivativeOption = getActivity().findViewById(R.id.unitDerivativeOption);
        unitCurrencyfuture = getActivity().findViewById(R.id.unitCurrencyfuture);
        unitCurrencyoption = getActivity().findViewById(R.id.unitCurrencyoption);
        unitCommodityfuture = getActivity().findViewById(R.id.unitCommodityfuture);
        unitCommodityoption = getActivity().findViewById(R.id.unitCommodityoption);
        unitStockIntraday = getActivity().findViewById(R.id.unitStockIntraday);
        discountBrokerage = getActivity().findViewById(R.id.discountBrokerage);
        premiumBrokerage = getActivity().findViewById(R.id.premiumBrokerage);
        nominee_layoutone = getActivity().findViewById(R.id.nominee_layoutone);
        nominee_layouttwo = getActivity().findViewById(R.id.nominee_layouttwo);
        nominee_layoutthree = getActivity().findViewById(R.id.nominee_layoutthree);
        is_a_us_person_YES = getActivity().findViewById(R.id.is_a_us_person_YES);
        is_a_us_person_NO = getActivity().findViewById(R.id.is_a_us_person_NO);
        discountMenu = getActivity().findViewById(R.id.discountMenu);
        premiumMenu = getActivity().findViewById(R.id.premiumMenu);
        addressProof = getActivity().findViewById(R.id.addressProof);
        brokerageScroll = getActivity().findViewById(R.id.brokerageScroll);
        discountstockdelivery = getActivity().findViewById(R.id.discount_stock_delivery);
        discountstockintraday = getActivity().findViewById(R.id.discount_stock_intraday);
        discountderivativesfutures = getActivity().findViewById(R.id.discount_derivatives_futures);
        discountderivativesoptions = getActivity().findViewById(R.id.discount_derivatives_options);
        discountcurrencyderivativesfutures = getActivity().findViewById(R.id.discount_currency_derivatives_futures);
        discountcurrencyderivativesoptions = getActivity().findViewById(R.id.discount_currency_derivatives_options);
        discountcommodityderivativesfutures = getActivity().findViewById(R.id.discount_commodity_derivatives_futures);
        discountcommodityderivativesoptions = getActivity().findViewById(R.id.discount_commodity_derivatives_options);
        addone = getActivity().findViewById(R.id.addone);
        addtwo = getActivity().findViewById(R.id.addtwo);
        addthree = getActivity().findViewById(R.id.addthree);
        addpincode = getActivity().findViewById(R.id.addpincode);
        dobofnominee1=getActivity().findViewById(R.id.dobofnominee1);
        dobofnominee1.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");
                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {sel++;}
                    if (clean.equals(cleanC)) sel--;
                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));
                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        System.out.println("year==>" + year);
                        cal.set(Calendar.YEAR, year);
                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                        Calendar calendar = Calendar.getInstance();
                        int currentyear = calendar.get(Calendar.YEAR);
                        int yeardiff=currentyear-year;
                        System.out.println("DOB of One" +year+"-"+(mon+1)+"-"+day);
                        DOB1=year+"-"+(mon+1)+"-"+day;
                        if(yeardiff<=18){
                            minorToggle_news.setChecked(true);
                            main_nominee_layoutsss.setVisibility(View.VISIBLE);
                            guardiandetailslayout.setVisibility(View.VISIBLE);
                            toggleValue = "yes";
                            isminornominee=true;
                        }else{
                            isminornominee=false;
                            toggle_button.setVisibility(View.GONE);
                            guardiandetailslayout.setVisibility(View.GONE);
                            toggleValue = "no";
                        }}

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));
                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dobofnominee1.setText(current);
                    dobofnominee1.setSelection(sel < current.length() ? sel : current.length());
                }}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("afterTextChanged data" + new String(s.toString()));
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
           });

        dobofnominee2=getActivity().findViewById(R.id.dobofnominee2);
        dobofnominee3=getActivity().findViewById(R.id.dobofnominee3);

        dobofnominee2.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");
                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {sel++;}
                    if (clean.equals(cleanC)) sel--;
                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));
                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        System.out.println("year==>" + year);
                        cal.set(Calendar.YEAR, year);
                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);

                        System.out.println("DOB of One" +year+"-"+(mon+1)+"-"+day);
                        DOB2=year+"-"+(mon+1)+"-"+day;
                        Calendar calendar = Calendar.getInstance();
                        int currentyear = calendar.get(Calendar.YEAR);
                        int yeardiff=currentyear-year;
                        if(yeardiff<=18){
                            guardiandetailslayout_secondNominee.setVisibility(View.VISIBLE);
                            is_Second_minornominee=true;
                        }else{
                            is_Second_minornominee=false;
                            guardiandetailslayout_secondNominee.setVisibility(View.GONE);

                        }
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));
                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dobofnominee2.setText(current);
                    dobofnominee2.setSelection(sel < current.length() ? sel : current.length());
                }}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("afterTextChanged data" + new String(s.toString()));
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dobofnominee3.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");
                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {sel++;}
                    if (clean.equals(cleanC)) sel--;
                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));
                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        System.out.println("year==>" + year);
                        cal.set(Calendar.YEAR, year);
                        System.out.println("DOB of One" +year+"-"+(mon+1)+"-"+day);
                        DOB3=year+"-"+(mon+1)+"-"+day;
                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                        Calendar calendar = Calendar.getInstance();
                        int currentyear = calendar.get(Calendar.YEAR);
                        int yeardiff=currentyear-year;
                        if(yeardiff<=18){
                            guardiandetailslayout_thirdNominee.setVisibility(View.VISIBLE);
                            is_Third_minornominee=true;

                        }else{
                            is_Third_minornominee=false;
                            //toggle_button.setVisibility(View.GONE);
                            guardiandetailslayout_thirdNominee.setVisibility(View.GONE);
                            //toggleValue = "no";
                        }
                       }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));
                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dobofnominee3.setText(current);
                    dobofnominee3.setSelection(sel < current.length() ? sel : current.length());
                 }}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("afterTextChanged data" + new String(s.toString()));
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        discountMenu.setBackgroundResource(R.drawable.gray_btn);
        edt_name_of_nominee2 = getActivity().findViewById(R.id.edt_name_of_nominee2);
        edtpercentagesharing2 = getActivity().findViewById(R.id.edtpercentagesharing2);
        edtpercentagesharing2.setFilters(new InputFilter[]{ new InputFilterMinMax("1.00", "100.00"),new InputFilter.LengthFilter(5) {
                }
                }
             );

         addres_one_of_nominee2 = getActivity().findViewById(R.id.addres_one_of_nominee2);
         addres_two_of_nominee2 = getActivity().findViewById(R.id.addres_two_of_nominee2);
         addres_three_of_nominee2 = getActivity().findViewById(R.id.addres_three_of_nominee2);
         addpincode2 = getActivity().findViewById(R.id.addpincode2);
         addcountry2 = getActivity().findViewById(R.id.addcountry2);
         edt_gurdianname2 = getActivity().findViewById(R.id.edt_gurdianname2);
         edt_gurdianaddress2 = getActivity().findViewById(R.id.edt_gurdianaddress2);
         edt_gurdianaddress_two2 = getActivity().findViewById(R.id.edt_gurdianaddress_two2);
         edt_gurdianaddress_three2 = getActivity().findViewById(R.id.edt_gurdianaddress_three2);
         edittextpincode2 = getActivity().findViewById(R.id.edittextpincode2);
         relationshipapplicant2 = getActivity().findViewById(R.id.relationshipapplicant2);
         relationshipapplicant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relationshipmethod(relationshipapplicant2);
            }});
         addstate2 = getActivity().findViewById(R.id.addstate2);
         percentagesharingofnomineeone2 = getActivity().findViewById(R.id.percentagesharingofnomineeone2);
         addcity2 = getActivity().findViewById(R.id.addcity2);
         txt_gurdian_city2 = getActivity().findViewById(R.id.txt_gurdian_city2);
         txt_gurdian_state2 = getActivity().findViewById(R.id.txt_gurdian_state2);
         edt_nameofnominee3 = getActivity().findViewById(R.id.edt_nameofnominee3);
         edtpercentagesharing3 = getActivity().findViewById(R.id.edtpercentagesharing3);
         edtpercentagesharing3.setFilters(new InputFilter[]{ new InputFilterMinMax("1.00", "100.00"),new InputFilter.LengthFilter(5) {
                }});
         relationshipapplicant3 = getActivity().findViewById(R.id.relationshipapplicant3);
         relationshipapplicant3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relationshipmethod(relationshipapplicant3);
            }
           });
        addone3 = getActivity().findViewById(R.id.addone3);
        addtwo3 = getActivity().findViewById(R.id.addtwo3);
        addthree3 = getActivity().findViewById(R.id.addthree3);
        addpincode3 = getActivity().findViewById(R.id.addpincode3);
        addcountry3 = getActivity().findViewById(R.id.addcountry3);
        edt_gurdianname3 = getActivity().findViewById(R.id.edt_gurdianname3);
        edt_gurdianaddress_one3 = getActivity().findViewById(R.id.edt_gurdianaddress_one3);
        edt_gurdianaddress_two3 = getActivity().findViewById(R.id.edt_gurdianaddress_two3);
        edt_gurdianaddress_three3 = getActivity().findViewById(R.id.edt_gurdianaddress_three3);
        edittextpincode3 = getActivity().findViewById(R.id.edittextpincode3);
        addstate3 = getActivity().findViewById(R.id.addstate3);
        addcity3 = getActivity().findViewById(R.id.addcity3);
        txt_gurdian_city3 = getActivity().findViewById(R.id.txt_gurdian_city3);
        txt_gurdian_state3 = getActivity().findViewById(R.id.txt_gurdian_state3);
        gurdian_address1=getActivity().findViewById(R.id.gurdian_address1);
        gurdian_address2=getActivity().findViewById(R.id.gurdian_address2);
        gurdian_address3=getActivity().findViewById(R.id.gurdian_address3);
        edt_gurdiannameone = getActivity().findViewById(R.id.edt_gurdiannameone);
        edt_gurdianaddress_one = getActivity().findViewById(R.id.edt_gurdianaddress_one);
        edt_gurdianaddress_two = getActivity().findViewById(R.id.edt_gurdianaddress_two);
        edt_gurdianaddress_three = getActivity().findViewById(R.id.edt_gurdianaddress_three);
        editgurdianpincode = getActivity().findViewById(R.id.editgurdianpincode);
        txt_gurdian_city = getActivity().findViewById(R.id.txt_gurdian_city);
        txt_gurdian_state = getActivity().findViewById(R.id.txt_gurdian_state);
        discountMenu.setTextColor(Color.parseColor("#A2A2A2"));
        discountBrokerage.setVisibility(View.GONE);
        premiumMenu.setBackgroundResource(R.mipmap.bottombtn);
        premiumMenu.setTextColor(Color.parseColor("#ffffff"));
        turnOverEdit = getActivity().findViewById(R.id.turnOverEdit);
        edtothername = getActivity().findViewById(R.id.edtothername);
        addressProofImage = getActivity().findViewById(R.id.addressProofImage);
        brokageImg = getActivity().findViewById(R.id.brokageImg);
        varificationVideo = getActivity().findViewById(R.id.varificationVideo);
        brokrafeNotes = getActivity().findViewById(R.id.brokrafeNotes);
        mobileLinkText = getActivity().findViewById(R.id.mobileLinkText);
        howToLonkTextView = getActivity().findViewById(R.id.howToLonkTextView);
        viewBrockrageReview = getActivity().findViewById(R.id.viewBrockrageReview);
        identificationproof = getActivity().findViewById(R.id.identificationproof);
        identificationproof_of_secondnomine=getActivity().findViewById(R.id.identificationproof_of_secondnomine);
        identificationproof_of_thirdnomine=getActivity().findViewById(R.id.identificationproof_of_thirdnomine);
        gurdianidentificationdocument=getActivity().findViewById(R.id.gurdianidentificationdocument);
        Second_gurdianidentificationdocument=getActivity().findViewById(R.id.Second_gurdianidentificationdocument);
        gurdianidentificationdocument_thirdnominee=getActivity().findViewById(R.id.gurdianidentificationdocument_thirdnominee);
        pancardlinearlayout = getActivity().findViewById(R.id.pancardlinearlayout);
        save_brockrage = getActivity().findViewById(R.id.save_brockrage);
        llSAveBrokrage = getActivity().findViewById(R.id.llSAveBrokrage);
        nomineetabslayout = getActivity().findViewById(R.id.nomineetabslayout);
        guardiandetailslayout = getActivity().findViewById(R.id.guardiandetailslayout);
        guardiandetailslayout_secondNominee=getActivity().findViewById(R.id.guardiandetailslayout_secondNominee);
        guardiandetailslayout_thirdNominee=getActivity().findViewById(R.id.guardiandetailslayout_thirdNominee);
        if(!minorToggle_news.isChecked()){
            guardiandetailslayout.setVisibility(View.GONE);
        }
        closeBrockrageBox = getActivity().findViewById(R.id.closeBrockrageBox);
        closeAdditonalDocs = getActivity().findViewById(R.id.closeAdditonalDocs);
        document_typeNominee_1= getActivity().findViewById(R.id.document_typeNominee_1);
        document_typeNominee_2= getActivity().findViewById(R.id.document_typeNominee_2);
        document_typeNominee_3= getActivity().findViewById(R.id.document_typeNominee_3);
        document_typeNomineeGurdian_1= getActivity().findViewById(R.id.document_typeNomineeGurdian_1);
        document_typeNomineeGurdian_2= getActivity(). findViewById(R.id.document_typeNomineeGurdian_2);
        document_typeNomineeGurdian_3= getActivity().findViewById(R.id.document_typeNomineeGurdian_3);
        DocumentType();
        closeAddNominee = getActivity().findViewById(R.id.closeAddNominee);
        closeAddNominees = getActivity().findViewById(R.id.closeAddNominees);
        submit_btn_nomine_detail = getActivity().findViewById(R.id.submit_btn_nomine_details);
        submit_btn_getLoan_detail = getActivity().findViewById(R.id.submit_btn_getLoan_detail);
        getLoanDetailLayout = getActivity().findViewById(R.id.getLoanDetailLayout);
        Cash_Intraday_ArrayList = new ArrayList<BrokerageModel>();
        Cash_Intraday_Array = new ArrayList<String>();
        Cash_Intraday_Array_description = new ArrayList<String>();
        Cash_Delivery_ArrayList = new ArrayList<BrokerageModel>();
        Cash_Delivery_Array = new ArrayList<String>();
        Cash_Delivery_Array_description = new ArrayList<String>();
        derivative_future_ArrayList = new ArrayList<BrokerageModel>();
        derivative_future_Array = new ArrayList<String>();
        derivatives_options_ArrayList = new ArrayList<BrokerageModel>();
        derivatives_options_Array = new ArrayList<String>();
        currency_derivatives_futures_ArrayList = new ArrayList<BrokerageModel>();
        currency_derivatives_futures_Array = new ArrayList<String>();
        currency_derivatives_options_ArrayList = new ArrayList<BrokerageModel>();
        currency_derivatives_options_Array = new ArrayList<String>();

        commodity_derivatives_futures_ArrayList = new ArrayList<BrokerageModel>();
        commodity_derivatives_futures_Array = new ArrayList<String>();

        commodity_derivatives_options_ArrayList = new ArrayList<BrokerageModel>();
        commodity_derivatives_options_Array = new ArrayList<String>();


        relationshipapplicant = getActivity().findViewById(R.id.relationshipapplicant);

        relationshipapplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relationshipmethod(relationshipapplicant);
            }
        });

        relationshipapplicant_guardian= getActivity().findViewById(R.id.relationshipapplicant_guardian);

        relationshipapplicant_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relationshipmethod(relationshipapplicant_guardian);
            }
        });

        relationshipapplicant_guardian2= getActivity().findViewById(R.id.relationshipapplicant_guardian2);

        relationshipapplicant_guardian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relationshipmethod(relationshipapplicant_guardian2);
            }
        });
        relationshipapplicant_guardian3= getActivity().findViewById(R.id.relationshipapplicant_guardian3);
        relationshipapplicant_guardian3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Relationshipmethod(relationshipapplicant_guardian3);
            }
        });
        //SAMEDATA
        stock_intraday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Api Intraday", "Calling");
                System.out.println("Api Intraday :" + "Calling Again");
                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=CASH_INTRADAY";

                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(WHITE).fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }
                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();
                    Cash_Intraday_Array_description.clear();
                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //.addJSONObjectBody(AdditionalObj)//posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("Api Intraday :" + "Calling Again Again");
                                    System.out.println("GetBrokerageDetail : stock_intraday : " + "response : " + response.toString());
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");
                                        System.out.println("GetBrokerageDetail :  stock_intraday : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : stock_intraday : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");

                                            if (data.has("NSE_CASH_INTRADAY_BrokerageDetail")) {

                                                NSE_CASH_INTRADAY_BrokerageDetail = data.getJSONArray("NSE_CASH_INTRADAY_BrokerageDetail");
                                                System.out.println("GetBrokerageDetailIntraday" + "" + NSE_CASH_INTRADAY_BrokerageDetail);

                                                if (NSE_CASH_INTRADAY_BrokerageDetail != null && NSE_CASH_INTRADAY_BrokerageDetail.length() != 0) {

                                                    System.out.println("Api Intraday :" + "Calling Again Again Again == " + NSE_CASH_INTRADAY_BrokerageDetail.length());
                                                    for (int i = 0; i < NSE_CASH_INTRADAY_BrokerageDetail.length(); i++) {

                                                        JSONObject Cash_intraday_obj = NSE_CASH_INTRADAY_BrokerageDetail.getJSONObject(i);
                                                        String MODULE_NO = Cash_intraday_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = Cash_intraday_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = Cash_intraday_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = Cash_intraday_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = Cash_intraday_obj.getString("DESCRIPTION");
                                                        String DESCRIPTION_INFO = Cash_intraday_obj.getString("DESCRIPTION_INFO");

                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "ONESIDEPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "ONESIDEMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail :stock_intraday " + "DESCRIPTION : " + DESCRIPTION);
                                                        System.out.println("GetBrokerageDetail :stock_intraday " + "DESCRIPTION_INFO : " + DESCRIPTION_INFO);

                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, DESCRIPTION_INFO);

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEPER);
                                                        Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_brokerage_Intraday = new RecyclerCustomAdapter_Intraday(getActivity(), Cash_Intraday_ArrayList);

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_brokerage_Intraday);

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            filter_Intraday(newText);
                                                            return false;
                                                        }
                                                    });
                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        } } }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : :stock_intraday : " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    } }});

                }catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        } } } }
        });


        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */

        stock_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=CASH_DELIVERY";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if(acProgressFlower != null) {
                        acProgressFlower.show();
                    }
                    Cash_Delivery_ArrayList.clear();
                    Cash_Delivery_Array.clear();
                    Cash_Delivery_Array_description.clear();
                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response
                                    System.out.println("GetBrokerageDetail : stock_delivery : " + "response : " + response.toString());
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");
                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "message : " + message);

                                        if(status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("NSE_CASH_DELIVERY_BrokerageDetail")) {
                                                JSONArray NSE_CASH_DELIVERY_BrokerageDetail = data.getJSONArray("NSE_CASH_DELIVERY_BrokerageDetail");
                                                System.out.println("GetBrokerageDetailDurgesh : " + "" + NSE_CASH_DELIVERY_BrokerageDetail);

                                                if (NSE_CASH_DELIVERY_BrokerageDetail != null && NSE_CASH_DELIVERY_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < NSE_CASH_DELIVERY_BrokerageDetail.length(); i++) {
                                                        JSONObject Cash_delivery_obj = NSE_CASH_DELIVERY_BrokerageDetail.getJSONObject(i);
                                                        String MODULE_NO = Cash_delivery_obj.getString("MODULE_NO");
                                                        String DELIVERYPER = Cash_delivery_obj.getString("DELIVERYPER");
                                                        String DELIVERYMIN = Cash_delivery_obj.getString("DELIVERYMIN");
                                                        String COMPANY_CODE = Cash_delivery_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = Cash_delivery_obj.getString("DESCRIPTION");
                                                        String DESCRIPTION_INFO = Cash_delivery_obj.getString("DESCRIPTION_INFO");

                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "DELIVERYPER : " + DELIVERYPER);
                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "DELIVERYMIN : " + DELIVERYMIN);
                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "DESCRIPTION : " + DESCRIPTION);
                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "DESCRIPTION_INFO : " + DESCRIPTION_INFO);

                                                        Cash_Delivery_ArrayList.add(new BrokerageModel(MODULE_NO, DELIVERYPER, DELIVERYMIN, COMPANY_CODE, DESCRIPTION, DESCRIPTION_INFO));
                                                        Cash_Delivery_Array.add(DELIVERYPER);
                                                        Cash_Delivery_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "Cash_Delivery_ArrayList : " + Cash_Delivery_ArrayList);
                                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "Cash_Delivery_Array : " + Cash_Delivery_Array);

                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();


                                                    stringArrayAdapter_brokerage_Delivery = new RecyclerCustomAdapter_Delivery(getActivity(), Cash_Delivery_ArrayList);
                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_brokerage_Delivery);

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String query) {
                                                            return false;
                                                        }
                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            filter_delivery(newText);
                                                            return false;
                                                        }
                                                    });

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();

                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();

                                                        }
                                                    }
                                                }
                                            } }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : stock_delivery : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : stock_delivery :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    } }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();

                        } } } }});

        //new code derivative_future

        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */
        derivatives_futures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=FNO_FUTURE";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(WHITE).fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }
                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();
                    AndroidNetworking.get(url).setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            .setTag("test").setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //do anything with response
                                    System.out.println("GetBrokerageDetail : derivatives_futures : " + "response : " + response.toString());
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");
                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("NSE_FNO_Future_BrokerageDetail")) {
                                                JSONArray NSE_FNO_Future_BrokerageDetail = data.getJSONArray("NSE_FNO_Future_BrokerageDetail");

                                                System.out.println("GetBrokerageDetail : derivatives_futures : " + "NSE_CASH_DELIVERY_BrokerageDetail : " + NSE_FNO_Future_BrokerageDetail);

                                                if (NSE_FNO_Future_BrokerageDetail != null && NSE_FNO_Future_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < NSE_FNO_Future_BrokerageDetail.length(); i++) {
                                                        JSONObject derivative_future_obj = NSE_FNO_Future_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = derivative_future_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = derivative_future_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = derivative_future_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = derivative_future_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = derivative_future_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "DESCRIPTION : " + DESCRIPTION);

                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, "");

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEPER);
                                                        //Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_DerivativeFuture = new RecyclerCustomAdapter_DerivativeFuture(getActivity(), Cash_Intraday_ArrayList);

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_DerivativeFuture);

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();
                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                                                          @Override
                                                                                          public boolean onQueryTextSubmit(String newText) {
                                                                                              return false;
                                                                                          }

                                                                                          @Override
                                                                                          public boolean onQueryTextChange(String newText) {
                                                                                              //filter_Intraday(newText);

                                                                                              filter_DerivativeFuture(newText);
                                                                                              return false;
                                                                                          }
                                                                                      }
                                                    );


                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : derivatives_futures :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    // handle error
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }
            }
        });


        //old code
    /*
    derivatives_futures.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        hideKeyboardReview(getActivity());
        String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail +"?KeyFor=FNO_FUTURE";

        try{
          acProgressFlower = new ACProgressFlower.Builder(getActivity())
                  .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                  .themeColor(WHITE)
                  .fadeColor(DKGRAY).build();
          if (acProgressFlower != null)
          {
            acProgressFlower.show();
          }

          derivative_future_ArrayList.clear();
          derivative_future_Array.clear();

          AndroidNetworking.get(url)
                  .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                  //  .addJSONObjectBody(AdditionalObj) // posting json
                  .setTag("test")
                  .setPriority(Priority.MEDIUM)
                  .build()
                  .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                      // do anything with response
                      System.out.println("GetBrokerageDetail : derivatives_futures : "+ "response : " + response.toString());

                      try {

                        String status = response.getString("status");
                        String message = response.getString("message");
                        System.out.println("GetBrokerageDetail : derivatives_futures : "+ "status : " + status);
                        System.out.println("GetBrokerageDetail : derivatives_futures : "+ "message : " + message);

                        if(status.equalsIgnoreCase("success"))
                        {
                          JSONObject data = response.getJSONObject("data");
                          if(data.has("NSE_FNO_Future_BrokerageDetail")){
                            JSONArray NSE_FNO_Future_BrokerageDetail =  data.getJSONArray("NSE_FNO_Future_BrokerageDetail");

                            System.out.println("GetBrokerageDetail : derivatives_futures : "+ "NSE_CASH_DELIVERY_BrokerageDetail : " + NSE_FNO_Future_BrokerageDetail);

                            if (NSE_FNO_Future_BrokerageDetail != null && NSE_FNO_Future_BrokerageDetail.length() != 0)

                            {
                              for (int i = 0 ; i < NSE_FNO_Future_BrokerageDetail.length() ; i++)
                              {
                                JSONObject derivative_future_obj = NSE_FNO_Future_BrokerageDetail.getJSONObject(i);

                                String MODULE_NO = derivative_future_obj.getString("MODULE_NO");
                                String ONESIDEPER = derivative_future_obj.getString("ONESIDEPER");
                                String ONESIDEMIN = derivative_future_obj.getString("ONESIDEMIN");
                                String COMPANY_CODE = derivative_future_obj.getString("COMPANY_CODE");
                                String DESCRIPTION = derivative_future_obj.getString("DESCRIPTION");

                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "MODULE_NO : " + MODULE_NO);
                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "DELIVERYPER : " + ONESIDEPER);
                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "DELIVERYMIN : " + ONESIDEMIN);
                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "COMPANY_CODE : " + COMPANY_CODE);
                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "DESCRIPTION : " + DESCRIPTION);

                                derivative_future_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER , ONESIDEMIN ,COMPANY_CODE , DESCRIPTION ,""));
                                derivative_future_Array.add(ONESIDEPER);

                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "derivative_future_ArrayList : " + derivative_future_ArrayList);
                                System.out.println("GetBrokerageDetail : derivatives_futures : "+ "derivative_future_Array : " + derivative_future_Array);
                              }

                              final Dialog dialog = new Dialog(getActivity());
                              LayoutInflater inflater = LayoutInflater.from(getActivity());
                              View view1 = inflater.inflate(R.layout.search_dialog, null);
                              final ListView listView = view1.findViewById(R.id.listView);
                              SearchView searchView = view1.findViewById(R.id.searchView);
                              searchView.clearFocus();
                              //searchView.requestFocus();

                              final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                                      derivative_future_Array);

                              listView.setAdapter(stringArrayAdapter);
                              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                  String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                                  System.out.println("GetBrokerageDetail : derivatives_futures : "+ "selectedFromList : " + selectedFromList);
                                  derivative_future_MODULE_NO =  derivative_future_ArrayList.get(i).getMODULE_NO();
                                  System.out.println("GetBrokerageDetail : derivatives_futures : "+ "derivative_future_MODULE_NO : " + derivative_future_MODULE_NO);
                                  derivative_future_COMPANY_CODE =  derivative_future_ArrayList.get(i).getCOMPANY_CODE();
                                  System.out.println("GetBrokerageDetail : derivatives_futures : "+ "derivative_future_COMPANY_CODE : " + derivative_future_COMPANY_CODE);
                                  dialog.dismiss();
                                  derivatives_futures.setText(selectedFromList);

                                }
                               });
                              searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String newText) {
                                  return false;
                                }
                                @Override
                                public boolean onQueryTextChange(String newText) {
                                  stringArrayAdapter.getFilter().filter(newText);
                                  return false;
                                }
                              });

                              dialog.setContentView(view1);
                              dialog.show();

                              if(acProgressFlower != null) {
                                if (acProgressFlower.isShowing()) {
                                  acProgressFlower.dismiss();
                                }
                              }
                            }
                            else {
                              Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                              if (acProgressFlower != null) {
                                if (acProgressFlower.isShowing()) {
                                  acProgressFlower.dismiss();
                                }
                              } } } }
                      }catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("GetBrokerageDetail : derivatives_futures : "+ "JSONException : " + e.getMessage());
                        if (acProgressFlower != null) {
                          if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                          }
                        }
                      }
                    }

                    @Override
                    public void onError(ANError error) {
                      System.out.println("GetBrokerageDetail : derivatives_futures :  "+ "error : " + error.getErrorBody());
                      if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                          acProgressFlower.dismiss();
                        }
                      }
                      // handle error
                    }
                  });
        } catch (Exception e) {
          e.printStackTrace();
          if (acProgressFlower != null) {
            if (acProgressFlower.isShowing()) {
              acProgressFlower.dismiss();
              }}
            }}
          });
          */

        //old code
        //DURGESH
        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */
        derivatives_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=FNO_OPTION";

                System.out.println("GetBrokerageDetail : derivatives_options ===" + url);
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(WHITE).fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }


                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();

                    //derivatives_options_ArrayList.clear();
                    //derivatives_options_Array.clear();

                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //do anything with response
                                    //durgeshchoudhary
                                    System.out.println("GetBrokerageDetail : derivatives_options : " + "response : " + response.toString());
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");
                                        System.out.println("GetBrokerageDetail : derivatives_options : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : derivatives_options : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("NSE_FNO_Option_BrokerageDetail")) {
                                                JSONArray NSE_FNO_Option_BrokerageDetail = data.getJSONArray("NSE_FNO_Option_BrokerageDetail");
                                                System.out.println("GetBrokerageDetail : derivatives_options : " + "NSE_FNO_Option_BrokerageDetail : " + NSE_FNO_Option_BrokerageDetail);

                                                if (NSE_FNO_Option_BrokerageDetail != null && NSE_FNO_Option_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < NSE_FNO_Option_BrokerageDetail.length(); i++) {
                                                        JSONObject derivative_future_obj = NSE_FNO_Option_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = derivative_future_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = derivative_future_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = derivative_future_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = derivative_future_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = derivative_future_obj.getString("DESCRIPTION");
                                                        //String DESCRIPTION_INFO = derivative_future_obj.getString("DESCRIPTION_INFO");

                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "ONESIDEPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "ONESIDEMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail :stock_intraday :" + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail :stock_intraday " + "DESCRIPTION : " + DESCRIPTION);
                                                        // System.out.println("GetBrokerageDetail :stock_intraday " + "DESCRIPTION_INFO : " + DESCRIPTION_INFO);

                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, "");

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEPER);
                                                        //Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();


                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_DerivativeOption = new RecyclerCustomAdapter_DerivativeOption(getActivity(), Cash_Intraday_ArrayList);


                              /*stringArrayAdapter_brokerage_Intraday = new RecyclerCustomAdapter_Intraday (getActivity(),
                                      Cash_Intraday_ArrayList);*/

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_DerivativeOption);

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();
                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                                                          @Override
                                                                                          public boolean onQueryTextSubmit(String newText) {
                                                                                              return false;
                                                                                          }

                                                                                          @Override
                                                                                          public boolean onQueryTextChange(String newText) {
                                                                                              //filter_Intraday(newText);

                                                                                              filter_DerivativeOption(newText);

                                                                                              return false;
                                                                                          }
                                                                                      }
                                                    );


                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : derivatives_options : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : derivatives_options :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }
            }
        });


        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */
        currency_derivatives_futures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());

                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Currency_Future";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }
                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();
                    //currency_derivatives_futures_ArrayList.clear();
                    //currency_derivatives_futures_Array.clear();

                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //  .addJSONObjectBody(AdditionalObj) // posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    // do anything with response
                                    System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "response : " + response.toString());

                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("CD_NSE_Future_BrokerageDetail")) {
                                                JSONArray CD_NSE_Future_BrokerageDetail = data.getJSONArray("CD_NSE_Future_BrokerageDetail");


                                                System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "CD_NSE_Future_BrokerageDetail : " + CD_NSE_Future_BrokerageDetail);

                                                if (CD_NSE_Future_BrokerageDetail != null && CD_NSE_Future_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < CD_NSE_Future_BrokerageDetail.length(); i++) {
                                                        JSONObject derivative_future_obj = CD_NSE_Future_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = derivative_future_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = derivative_future_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = derivative_future_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = derivative_future_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = derivative_future_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "DESCRIPTION : " + DESCRIPTION);

                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, "");

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEPER);
                                                        //Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_currencyDerivativeFuture = new RecyclerCustomAdapter_CurrencyDerivativeFuture(getActivity(), Cash_Intraday_ArrayList);

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_currencyDerivativeFuture);

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            filter_currencyDerivativeFuture(newText);
                                                            return false;
                                                        }
                                                    });

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();

                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }

                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : currency_derivatives_futures :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                }
                            });
                }catch(Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }
            }
        });


       /* currency_derivatives_futures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());

                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Currency_Future";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }

                    currency_derivatives_futures_ArrayList.clear();
                    currency_derivatives_futures_Array.clear();

                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //.addJSONObjectBody(AdditionalObj) // posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                   //do anything with response
                                    System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "response : " + response.toString());

                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("CD_NSE_Future_BrokerageDetail")) {
                                                JSONArray CD_NSE_Future_BrokerageDetail = data.getJSONArray("CD_NSE_Future_BrokerageDetail");


                                                System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "CD_NSE_Future_BrokerageDetail : " + CD_NSE_Future_BrokerageDetail);

                                                if (CD_NSE_Future_BrokerageDetail != null && CD_NSE_Future_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < CD_NSE_Future_BrokerageDetail.length(); i++) {
                                                        JSONObject derivative_future_obj = CD_NSE_Future_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = derivative_future_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = derivative_future_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = derivative_future_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = derivative_future_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = derivative_future_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "DESCRIPTION : " + DESCRIPTION);

                                                        currency_derivatives_futures_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, ""));
                                                        currency_derivatives_futures_Array.add(ONESIDEPER);

                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "currency_derivatives_futures_ArrayList : " + currency_derivatives_futures_ArrayList);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "currency_derivatives_futures_Array : " + currency_derivatives_futures_Array);
                                                    }

                                                    final Dialog dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                                                    final ListView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    // searchView.requestFocus();

                                                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                                                            getActivity(),
                                                            android.R.layout.simple_list_item_1,
                                                            currency_derivatives_futures_Array);

                                                    listView.setAdapter(stringArrayAdapter);
                                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                            String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                                                            System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "selectedFromList : " + selectedFromList);

                                                            currency_derivatives_futures_MODULE_NO = currency_derivatives_futures_ArrayList.get(i).getMODULE_NO();
                                                            System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "currency_derivatives_futures_MODULE_NO : " + currency_derivatives_futures_MODULE_NO);

                                                            currency_derivatives_futures_COMPANY_CODE = currency_derivatives_futures_ArrayList.get(i).getCOMPANY_CODE();
                                                            System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "currency_derivatives_futures_COMPANY_CODE : " + currency_derivatives_futures_COMPANY_CODE);

                                                            dialog.dismiss();

                                                            currency_derivatives_futures.setText(selectedFromList);
                                                        }
                                                    });

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            stringArrayAdapter.getFilter().filter(newText);
                                                            return false;
                                                        }
                                                    });

                                                    dialog.setContentView(view1);
                                                    dialog.show();

                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }

                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : currency_derivatives_futures :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }
            }
        });*/


        //currency_derivative_future

        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */
        currency_derivatives_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Currency_Option";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }

                    //currency_derivatives_options_ArrayList.clear();
                    //currency_derivatives_options_Array.clear();

                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();
                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //  .addJSONObjectBody(AdditionalObj) // posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "response : " + response.toString());

                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("CD_NSE_Option_BrokerageDetail")) {
                                                JSONArray CD_NSE_Option_BrokerageDetail = data.getJSONArray("CD_NSE_Option_BrokerageDetail");


                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "CD_NSE_Option_BrokerageDetail : " + CD_NSE_Option_BrokerageDetail);

                                                if (CD_NSE_Option_BrokerageDetail != null && CD_NSE_Option_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < CD_NSE_Option_BrokerageDetail.length(); i++) {
                                                        JSONObject currency_derivatives_options_obj = CD_NSE_Option_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = currency_derivatives_options_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = currency_derivatives_options_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = currency_derivatives_options_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = currency_derivatives_options_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = currency_derivatives_options_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "DESCRIPTION : " + DESCRIPTION);

                                                        // currency_derivatives_options_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, ""));
                                                        //currency_derivatives_options_Array.add(ONESIDEMIN);
                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, "");

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEMIN);
                                                        //Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_currencyDerivativeOption = new RecyclerCustomAdapter_CurrencyDerivativeOption(getActivity(), Cash_Intraday_ArrayList);

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_currencyDerivativeOption);

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            filter_currencyDerivativeOption(newText);
                                                            return false;
                                                        }
                                                    });

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();

                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }

                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : currency_derivatives_options :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    // handle error
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }

            }
        });








        //old code
       /* currency_derivatives_options.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideKeyboardReview(getActivity());
                        String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Currency_Option";
                        try {
                            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(WHITE)
                                    .fadeColor(DKGRAY).build();
                            if (acProgressFlower != null) {
                                acProgressFlower.show();
                            }

                            currency_derivatives_options_ArrayList.clear();
                            currency_derivatives_options_Array.clear();


                            AndroidNetworking.get(url)
                                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                                    //  .addJSONObjectBody(AdditionalObj) // posting json
                                    .setTag("test")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "response : " + response.toString());

                                            try {
                                                String status = response.getString("status");
                                                String message = response.getString("message");

                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "status : " + status);
                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "message : " + message);

                                                if (status.equalsIgnoreCase("success")) {
                                                    JSONObject data = response.getJSONObject("data");
                                                    if (data.has("CD_NSE_Option_BrokerageDetail")) {
                                                        JSONArray CD_NSE_Option_BrokerageDetail = data.getJSONArray("CD_NSE_Option_BrokerageDetail");


                                                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "CD_NSE_Option_BrokerageDetail : " + CD_NSE_Option_BrokerageDetail);

                                                        if (CD_NSE_Option_BrokerageDetail != null && CD_NSE_Option_BrokerageDetail.length() != 0) {
                                                            for (int i = 0; i < CD_NSE_Option_BrokerageDetail.length(); i++) {
                                                                JSONObject currency_derivatives_options_obj = CD_NSE_Option_BrokerageDetail.getJSONObject(i);

                                                                String MODULE_NO = currency_derivatives_options_obj.getString("MODULE_NO");
                                                                String ONESIDEPER = currency_derivatives_options_obj.getString("ONESIDEPER");
                                                                String ONESIDEMIN = currency_derivatives_options_obj.getString("ONESIDEMIN");
                                                                String COMPANY_CODE = currency_derivatives_options_obj.getString("COMPANY_CODE");
                                                                String DESCRIPTION = currency_derivatives_options_obj.getString("DESCRIPTION");

                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "MODULE_NO : " + MODULE_NO);
                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "DELIVERYPER : " + ONESIDEPER);
                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "DESCRIPTION : " + DESCRIPTION);

                                                                currency_derivatives_options_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, ""));
                                                                currency_derivatives_options_Array.add(ONESIDEMIN);

                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "currency_derivatives_options_ArrayList : " + currency_derivatives_options_ArrayList);
                                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "currency_derivatives_options_Array : " + currency_derivatives_options_Array);
                                                            }

                                                            final Dialog dialog = new Dialog(getActivity());
                                                            LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                            View view1 = inflater.inflate(R.layout.search_dialog, null);
                                                            final ListView listView = view1.findViewById(R.id.listView);
                                                            SearchView searchView = view1.findViewById(R.id.searchView);
                                                            searchView.requestFocus();

                                                            final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                                                                    getActivity(),
                                                                    android.R.layout.simple_list_item_1,
                                                                    currency_derivatives_options_Array);

                                                            listView.setAdapter(stringArrayAdapter);
                                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                @Override
                                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                                    String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                                                                    System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "selectedFromList : " + selectedFromList);

                                                                    currency_derivatives_options_MODULE_NO = currency_derivatives_options_ArrayList.get(i).getMODULE_NO();
                                                                    System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "currency_derivatives_options_MODULE_NO : " + currency_derivatives_options_MODULE_NO);

                                                                    currency_derivatives_options_COMPANY_CODE = currency_derivatives_options_ArrayList.get(i).getCOMPANY_CODE();
                                                                    System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "currency_derivatives_options_COMPANY_CODE : " + currency_derivatives_options_COMPANY_CODE);

                                                                    dialog.dismiss();

                                                                    currency_derivatives_options.setText(selectedFromList);
                                                                }
                                                            });

                                                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                                @Override
                                                                public boolean onQueryTextSubmit(String newText) {
                                                                    return false;
                                                                }

                                                                @Override
                                                                public boolean onQueryTextChange(String newText) {
                                                                    stringArrayAdapter.getFilter().filter(newText);
                                                                    return false;
                                                                }
                                                            });

                                                            dialog.setContentView(view1);
                                                            dialog.show();

                                                            if (acProgressFlower != null) {
                                                                if (acProgressFlower.isShowing()) {
                                                                    acProgressFlower.dismiss();
                                                                }
                                                            }

                                                        } else {
                                                            Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                            if (acProgressFlower != null) {
                                                                if (acProgressFlower.isShowing()) {
                                                                    acProgressFlower.dismiss();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "JSONException : " + e.getMessage());
                                                if (acProgressFlower != null) {
                                                    if (acProgressFlower.isShowing()) {
                                                        acProgressFlower.dismiss();
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onError(ANError error) {
                                            System.out.println("GetBrokerageDetail : currency_derivatives_options :  " + "error : " + error.getErrorBody());
                                            if (acProgressFlower != null) {
                                                if (acProgressFlower.isShowing()) {
                                                    acProgressFlower.dismiss();
                                                }
                                            }
                                            // handle error
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (acProgressFlower != null) {
                                if (acProgressFlower.isShowing()) {
                                    acProgressFlower.dismiss();
                                }
                            }
                        }

                    }
                });*/


        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */
        commodity_derivatives_futures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Commodity_Future";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();

                    }

                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();

                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //  .addJSONObjectBody(AdditionalObj) // posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    // do anything with response
                                    System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "response : " + response.toString());

                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("MCX_Future_BrokerageDetail")) {
                                                JSONArray MCX_Future_BrokerageDetail = data.getJSONArray("MCX_Future_BrokerageDetail");


                                                System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "MCX_Future_BrokerageDetail : " + MCX_Future_BrokerageDetail);

                                                if (MCX_Future_BrokerageDetail != null && MCX_Future_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < MCX_Future_BrokerageDetail.length(); i++) {
                                                        JSONObject MCX_Future_obj = MCX_Future_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = MCX_Future_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = MCX_Future_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = MCX_Future_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = MCX_Future_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = MCX_Future_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "DESCRIPTION : " + DESCRIPTION);

                                                        //commodity_derivatives_futures_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, ""));
                                                        //commodity_derivatives_futures_Array.add(ONESIDEPER);

                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, "");

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEPER);
                                                        //Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_commoditiyDerivativeFuture = new RecyclerCustomAdapter_CommoditiyDerivativeFuture(getActivity(), Cash_Intraday_ArrayList);

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_commoditiyDerivativeFuture);

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            //filter_currencyDerivativeOption(newText);
                                                            filter_commDerivativeOption(newText);

                                                            return false;
                                                        }
                                                    });

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();

                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }

                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : commodity_derivatives_futures :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    // handle error
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }

            }
        });

     /*   commodity_derivatives_futures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Commodity_Future";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }

                    commodity_derivatives_futures_ArrayList.clear();
                    commodity_derivatives_futures_Array.clear();

                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //  .addJSONObjectBody(AdditionalObj) // posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    // do anything with response
                                    System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "response : " + response.toString());

                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("MCX_Future_BrokerageDetail")) {
                                                JSONArray MCX_Future_BrokerageDetail = data.getJSONArray("MCX_Future_BrokerageDetail");


                                                System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "MCX_Future_BrokerageDetail : " + MCX_Future_BrokerageDetail);

                                                if (MCX_Future_BrokerageDetail != null && MCX_Future_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < MCX_Future_BrokerageDetail.length(); i++) {
                                                        JSONObject MCX_Future_obj = MCX_Future_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = MCX_Future_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = MCX_Future_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = MCX_Future_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = MCX_Future_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = MCX_Future_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "DESCRIPTION : " + DESCRIPTION);

                                                        commodity_derivatives_futures_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, ""));
                                                        commodity_derivatives_futures_Array.add(ONESIDEPER);

                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "commodity_derivatives_futures_ArrayList : " + commodity_derivatives_futures_ArrayList);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "commodity_derivatives_futures_Array : " + commodity_derivatives_futures_Array);
                                                    }

                                                    final Dialog dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                                                    final ListView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();
                                                    //searchView.requestFocus();

                                                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                                                            getActivity(),
                                                            android.R.layout.simple_list_item_1,
                                                            commodity_derivatives_futures_Array);

                                                    listView.setAdapter(stringArrayAdapter);
                                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                            String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                                                            System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "selectedFromList : " + selectedFromList);

                                                            commodity_derivatives_futures_MODULE_NO = commodity_derivatives_futures_ArrayList.get(i).getMODULE_NO();
                                                            System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "commodity_derivatives_futures_MODULE_NO : " + commodity_derivatives_futures_MODULE_NO);

                                                            commodity_derivatives_futures_COMPANY_CODE = commodity_derivatives_futures_ArrayList.get(i).getCOMPANY_CODE();
                                                            System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "commodity_derivatives_futures_COMPANY_CODE : " + commodity_derivatives_futures_COMPANY_CODE);

                                                            dialog.dismiss();
                                                            commodity_derivatives_futures.setText(selectedFromList);
                                                        }
                                                    });

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            stringArrayAdapter.getFilter().filter(newText);
                                                            return false;
                                                        }
                                                    });

                                                    dialog.setContentView(view1);
                                                    dialog.show();

                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }

                                                } else {
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : commodity_derivatives_futures : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : commodity_derivatives_futures :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    // handle error
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                }

            }
        });*/

        /**
         *In this Section Brockerage Rate and module Id  send  Properly
         */
        commodity_derivatives_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardReview(getActivity());
                String url = WebServices.getBaseURL + WebServices.GetBrokerageDetail + "?KeyFor=Commodity_Option";
                try {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    if (acProgressFlower != null) {
                        acProgressFlower.show();
                    }


                    Cash_Intraday_ArrayList.clear();
                    Cash_Intraday_Array.clear();

                    AndroidNetworking.get(url)
                            .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                            //  .addJSONObjectBody(AdditionalObj) // posting json
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "response : " + response.toString());
                                    try{
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "status : " + status);
                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "message : " + message);

                                        if (status.equalsIgnoreCase("success")) {
                                            JSONObject data = response.getJSONObject("data");
                                            if (data.has("MCX_Option_BrokerageDetail")) {
                                                JSONArray MCX_Option_BrokerageDetail = data.getJSONArray("MCX_Option_BrokerageDetail");


                                                System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "MCX_Option_BrokerageDetail : " + MCX_Option_BrokerageDetail);

                                                if (MCX_Option_BrokerageDetail != null && MCX_Option_BrokerageDetail.length() != 0) {
                                                    for (int i = 0; i < MCX_Option_BrokerageDetail.length(); i++) {
                                                        JSONObject MCX_Option_obj = MCX_Option_BrokerageDetail.getJSONObject(i);

                                                        String MODULE_NO = MCX_Option_obj.getString("MODULE_NO");
                                                        String ONESIDEPER = MCX_Option_obj.getString("ONESIDEPER");
                                                        String ONESIDEMIN = MCX_Option_obj.getString("ONESIDEMIN");
                                                        String COMPANY_CODE = MCX_Option_obj.getString("COMPANY_CODE");
                                                        String DESCRIPTION = MCX_Option_obj.getString("DESCRIPTION");

                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "MODULE_NO : " + MODULE_NO);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "DELIVERYPER : " + ONESIDEPER);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "DELIVERYMIN : " + ONESIDEMIN);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "COMPANY_CODE : " + COMPANY_CODE);
                                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "DESCRIPTION : " + DESCRIPTION);

                                                        //commodity_derivatives_options_ArrayList.add(new BrokerageModel(MODULE_NO, ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, ""));
                                                        //commodity_derivatives_options_Array.add(ONESIDEMIN);

                                                        BrokerageModel brokerageModel = new BrokerageModel(MODULE_NO,
                                                                ONESIDEPER, ONESIDEMIN, COMPANY_CODE, DESCRIPTION, "");

                                                        Cash_Intraday_ArrayList.add(brokerageModel);
                                                        Cash_Intraday_Array.add(ONESIDEMIN);
                                                        //Cash_Intraday_Array_description.add(DESCRIPTION_INFO);

                                                        System.out.println("GetBrokerageDetail : stock_intraday: " + "Cash_Intraday_ArrayList : " + Cash_Intraday_ArrayList);
                                                        System.out.println("GetBrokerageDetail :stock_intraday : " + "Cash_Intraday_ArrayDurgesh : " + Cash_Intraday_Array);
                                                    }

                                                    brokerage_dialog = new Dialog(getActivity());
                                                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                                                    View view1 = inflater.inflate(R.layout.search_for_brokerage, null);
                                                    final RecyclerView listView = view1.findViewById(R.id.listView);
                                                    SearchView searchView = view1.findViewById(R.id.searchView);
                                                    searchView.clearFocus();

                                                    //searchView.requestFocus();

                                                    stringArrayAdapter_commoditiyDerivativeOption = new RecyclerCustomAdapter_CommoditiyDerivativeOption(getActivity(), Cash_Intraday_ArrayList);

                                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                                    listView.setLayoutManager(linearLayoutManager);
                                                    listView.setAdapter(stringArrayAdapter_commoditiyDerivativeOption);

                                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                        @Override
                                                        public boolean onQueryTextSubmit(String newText) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onQueryTextChange(String newText) {
                                                            //filter_currencyDerivativeOption(newText);
                                                            filter_commDerivativeFuture(newText);
                                                            return false;
                                                        }
                                                    });

                                                    brokerage_dialog.setContentView(view1);
                                                    brokerage_dialog.show();

                                                    if(acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }}
                                                }else{
                                                    Toast.makeText(getActivity(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                                                    if (acProgressFlower != null) {
                                                        if (acProgressFlower.isShowing()) {
                                                            acProgressFlower.dismiss();
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "JSONException : " + e.getMessage());
                                        if (acProgressFlower != null) {
                                            if (acProgressFlower.isShowing()) {
                                                acProgressFlower.dismiss();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    System.out.println("GetBrokerageDetail : commodity_derivatives_options :  " + "error : " + error.getErrorBody());
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    // handle error
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }}}}
                   });
        if(AppInfo.IsLoanApplied.equals("Yes")) {
            getLoanDetailLayout.setVisibility(View.VISIBLE);
        }else{
            getLoanDetailLayout.setVisibility(View.GONE);
        }
        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
        submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
        submit_btn_getLoan_detail.setEnabled(true);
        getRes = new getResponse(getActivity());
        mobileLinkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAaadharQuestionDialog(getActivity());
            }});
        howToLonkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ADHAARSITE_URL = "https://uidai.gov.in/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(ADHAARSITE_URL));
                startActivity(intent);
            }});

        addressproofstr = AppInfo.getPreference(getActivity(), "addressproof");
        System.out.println("addressproofstr_review" + addressproofstr);
        if(addressproofstr.equals("0")) {
            userDocuments userDocs = FinalApplicationForm.getUserDocuments();
            if(userDocs != null) {
                addressproofstr = userDocs.getOtherAddressProofType();
            }else{
                addressproofstr = "Aadhar Card";
            }
           }else{
            addressproofstr = "Aadhar Card";
        }
        addressProof.setText(addressproofstr);
        bottomSheetBehaviorBrockrage = BottomSheetBehavior.from(getActivity().findViewById(R.id.selectBrockraglayoutBottomSheet));
        initListenersBrockrage();
        bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorBrockrage.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }

        });
        setUpAdditionalUI();
        radioyesbutton_yes.setChecked(false);
        radionobutton_no.setChecked(false);
        bottomSheetBehaviorAdditionDoc = BottomSheetBehavior.from(getActivity().findViewById(R.id.additionalDoc));
        bottomSheetBehaviorAddNominee = BottomSheetBehavior.from(getActivity().findViewById(R.id.addNominee));
        bottomSheetBehaviorAddNominees = BottomSheetBehavior.from(getActivity().findViewById(R.id.addNominees));
        initListenersAdditionalDoc();
        bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorAdditionDoc.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_EXPANDED);
                } }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        bottomSheetBehaviorAddNominee.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorAddNominee.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehaviorAddNominee.setState(BottomSheetBehavior.STATE_EXPANDED);
                }}

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }

        });

        bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorAddNominees.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_EXPANDED);

                } }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }

        });

        viewBrockrageReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBrokerageData();

            }
        });

        identificationproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDoc = "Nominee_First";
                pickupImage_of_Nominee(titleDoc);

            }});
        identificationproof_of_secondnomine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDoc = "Nominee_Second";
                pickupImage_of_Nominee(titleDoc);
            }
        });

        identificationproof_of_thirdnomine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDoc = "Nominee_Third";
                pickupImage_of_Nominee(titleDoc);
            }
        });

        Second_gurdianidentificationdocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDoc = "GurdianNominee_Second_Image";
                pickupImage_of_Nominee(titleDoc);
            }
        });


        gurdianidentificationdocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDoc = "GurdianNominee_First_Image";
                pickupImage_of_Nominee(titleDoc);
            }
        });
        gurdianidentificationdocument_thirdnominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleDoc = "GurdianNominee_Third_Image";
                pickupImage_of_Nominee(titleDoc);
            }
        });




        rl_uploadBrokrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AppInfo.IsReadonlyMode) {
                    titleDoc = "Brokerage";
                    pickupImage();
                }
            }
        });
        imageLoader = ImageLoader.getInstance();
        getRes = new getResponse(getActivity());
        reviewWeb = getActivity().findViewById(R.id.reviewWeb);
        EdtAdditionText = getActivity().findViewById(R.id.EdtAdditionText);

        addressEdit = getActivity().findViewById(R.id.addressEdit);
        userPANText = getActivity().findViewById(R.id.userPANText);
        addressText = getActivity().findViewById(R.id.addressText);
        userBankText = getActivity().findViewById(R.id.userBankText);
        userEditIncomeText = getActivity().findViewById(R.id.userIncomeeeText);
        addcity = getActivity().findViewById(R.id.addcity);
        addstate = getActivity().findViewById(R.id.addstate);
        addcountry = getActivity().findViewById(R.id.addcountry);
        userSelfieText = getActivity().findViewById(R.id.userSelfieText);
        userSignatureText = getActivity().findViewById(R.id.userSignatureText);
        userVarificationText = getActivity().findViewById(R.id.userVarificationText);
        llAddressProof = getActivity().findViewById(R.id.llAddressProof);
        EdtAdditionText.setOnClickListener(onClickListener);
        userPANText.setOnClickListener(onClickListener);
        addressText.setOnClickListener(onClickListener);
        addressEdit.setOnClickListener(onClickListener);
        userBankText.setOnClickListener(onClickListener);
        userEditIncomeText.setOnClickListener(onClickListener);
        userSelfieText.setOnClickListener(onClickListener);
        userSignatureText.setOnClickListener(onClickListener);
        userVarificationText.setOnClickListener(onClickListener);
        physicalUpload = getActivity().findViewById(R.id.physicalUpload);
        eSignComplete = getActivity().findViewById(R.id.eSignComplete);
        downloadPDF = getActivity().findViewById(R.id.downloadPDF);
        mainScrollview = getActivity().findViewById(R.id.mainScrollview);
        mainScrollview_new = getActivity().findViewById(R.id.mainScrollview_new);
        reviewWeb.setWebViewClient(new WebViewClient());
        reviewWeb.setWebChromeClient(new WebChromeClient());
        reviewWeb.getSettings().setJavaScriptEnabled(true);
        reviewWeb.loadUrl("https://www.cdslindia.com/aadhar/loginaadhar.aspx");

        if (addressproofstr.equals("Aadhar Card")) {
            llAddressProof.setVisibility(View.GONE);
        }else {
            llAddressProof.setVisibility(View.GONE);

        }

        userPanEditText = getActivity().findViewById(R.id.userPanCardEdit);
        userPanTextView = getActivity().findViewById(R.id.userPanCardText);
        userPanTextView.setOnClickListener(onClickListener);

        userDOBEditText = getActivity().findViewById(R.id.userDOBEdit);
        userClientCode = getActivity().findViewById(R.id.userClientCode);

        userDOBTextView = getActivity().findViewById(R.id.userDOBText);
        userDOBTextView.setOnClickListener(onClickListener);

        userNameEditText = getActivity().findViewById(R.id.userNameEdit);
        userNameTextView = getActivity().findViewById(R.id.userNameText);
        userNameTextView.setOnClickListener(onClickListener);

        userFNameEditText = getActivity().findViewById(R.id.userFNameEdit);
        userFNameTextView = getActivity().findViewById(R.id.userFNameText);
        userFNameTextView.setOnClickListener(onClickListener);

        userClientCode_pencil = getActivity().findViewById(R.id.userClientCode_pencil);
        userClientCode_pencil.setOnClickListener(onClickListener);

        userClientCode_Submit = getActivity().findViewById(R.id.userClientCode_Submit);
        userClientCode_Submit.setOnClickListener(onClickListener);

        userMNameEditText = getActivity().findViewById(R.id.userMNameEdit);
        userMNameTextView = getActivity().findViewById(R.id.userMNameText);
        userMNameTextView.setOnClickListener(onClickListener);

        userEmailEdit = getActivity().findViewById(R.id.userEmailEdit);
        userEmailText = getActivity().findViewById(R.id.userEmailText);
        userEmailText.setVisibility(View.GONE);

        userMobileEdit = getActivity().findViewById(R.id.userMobileEdit);
        userMobileText = getActivity().findViewById(R.id.userMobileText);
        userMobileText.setVisibility(View.GONE);

        userMaritalStatusEditText = getActivity().findViewById(R.id.userMaritalStatusEdit);
        userMaritalStatusTextView = getActivity().findViewById(R.id.userMaritalStatusText);
        userMaritalStatusTextView.setOnClickListener(onClickListener);

        userGenderTextView = getActivity().findViewById(R.id.userGenderText);
        userGenderEditText = getActivity().findViewById(R.id.userGenderEdit);
        userGenderTextView.setOnClickListener(onClickListener);

        userAddressEditText = getActivity().findViewById(R.id.userAddressEdit);
        userAddressTextview = getActivity().findViewById(R.id.userAddressText);
        userAddressTextview.setOnClickListener(onClickListener);

        userCityEditText = getActivity().findViewById(R.id.userCityEdit);

        userCityTextView = getActivity().findViewById(R.id.userCityText);
        userCityTextView.setOnClickListener(onClickListener);

        userPostcalCodeEditText = getActivity().findViewById(R.id.userPincodeEdit);

        userPostcalCodeTextView = getActivity().findViewById(R.id.userPincodeText);
        userPostcalCodeTextView.setOnClickListener(onClickListener);

        userStateEditText = getActivity().findViewById(R.id.userStateEdit);
        userStateTextView = getActivity().findViewById(R.id.userStateText);
        userStateTextView.setOnClickListener(onClickListener);

        userIncomeEditText = getActivity().findViewById(R.id.userIncomeEdit);
        userIncomeTextView = getActivity().findViewById(R.id.userIncomeText);
        userIncomeText = getActivity().findViewById(R.id.userIncomeText);
        userIncomeTextView.setOnClickListener(onClickListener);

        userOccupationEditText = getActivity().findViewById(R.id.userOccupationEdit);
        userOccupationTextView = getActivity().findViewById(R.id.userOccupationText);
        userOccupationTextView.setOnClickListener(onClickListener);

        userPolicalPartyEditText = getActivity().findViewById(R.id.userPoliticalEdit);
        userPolicalPartyTextView = getActivity().findViewById(R.id.userPoliticalText);
        userPolicalPartyTextView.setOnClickListener(onClickListener);

        userTradingEditText = getActivity().findViewById(R.id.userTradingEdit);
        userTradingTextView = getActivity().findViewById(R.id.userTradingText);
        userTradingTextView.setOnClickListener(onClickListener);

        userDematEditText = getActivity().findViewById(R.id.userDematEdit);
        userDematTextView = getActivity().findViewById(R.id.userDematText);
        userDematTextView.setOnClickListener(onClickListener);

        userSegmentsEditText = getActivity().findViewById(R.id.userSegmentEdit);
        usermtfEditText = getActivity().findViewById(R.id.usermtfEdit);
        userSegmentsTextView = getActivity().findViewById(R.id.userSegmentText);
        usermtfTextView = getActivity().findViewById(R.id.usermtfText);
        userSegmentsTextView.setOnClickListener(onClickListener);
        usermtfTextView.setOnClickListener(onClickListener);


        percentagesharingodnomineeone = getActivity().findViewById(R.id.percentagesharingofnomineeone);
        addressofnomineone = getActivity().findViewById(R.id.addressofnomineeone);


        additionalImageone = getActivity().findViewById(R.id.additionalImageone);
        additionalImagetwo = getActivity().findViewById(R.id.additionalImagetwo);
        userAadharFrontImage = getActivity().findViewById(R.id.userFrontImage);
        userAadharBackImage = getActivity().findViewById(R.id.userBackImage);
        userPanImage = getActivity().findViewById(R.id.userPANImage);

        nominee_oneImageview = getActivity().findViewById(R.id.nominee_oneImageview);
        gurdian_imageview=getActivity().findViewById(R.id.gurdian_imageview);
        gurdian_imageviewthird=getActivity().findViewById(R.id.gurdian_imageviewthird);
        gurdian_Second_imageview=getActivity().findViewById(R.id.gurdian_Second_imageview);
        second_nominee_image=getActivity().findViewById(R.id.second_nominee_image);
        Third_nominee_image=getActivity().findViewById(R.id.Third_nominee_image);

        userIncomeImage = getActivity().findViewById(R.id.userIncomeImage);
        userBankImage = getActivity().findViewById(R.id.userBankImage);
        userBankImage2 = getActivity().findViewById(R.id.userBankImage2);
        userSignatureImage = getActivity().findViewById(R.id.userSignatureImage);
        userPhotoImage = getActivity().findViewById(R.id.userSelfieImage);

        review_applicationMainLayout = getActivity().findViewById(R.id.review_applicationMainLayout);
        error_with_aadhar = getActivity().findViewById(R.id.error_with_aadhar);
        congratulationsLayout = getActivity().findViewById(R.id.congratulationsLayout);
        aadhar_not_linked = getActivity().findViewById(R.id.aadhar_not_linked);
        //additional_doc = (RelativeLayout) getActivity().findViewById(R.id.additionalDoc);
        reviewWebView = getActivity().findViewById(R.id.reviewWebView);
        client_code_section = getActivity().findViewById(R.id.client_code_section);
        client_code_editable_section = getActivity().findViewById(R.id.client_code_editable_section);

        main_nominee_layoutsss = getActivity().findViewById(R.id.main_nominee_layoutsss);



        if (radioGroupdurgesh.getCheckedRadioButtonId() == -1)
        {
            //no radio buttons are checked
            nomineetabslayout.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), "no durgesh ", Toast.LENGTH_SHORT).show();
            main_nominee_layoutsss.setVisibility(View.GONE);
            rl_addnominelayout.setVisibility(View.GONE);
            notelayout.setVisibility(View.GONE);
        }
        else
        {

        }

        userClientCode_edt_lable = getActivity().findViewById(R.id.userClientCode_edt_lable);
        userClientCode_edt = getActivity().findViewById(R.id.userClientCode_edt);
        userClientCode_edtitext = getActivity().findViewById(R.id.userClientCode_edtitext);
        account_plus_img = getActivity().findViewById(R.id.account_plus_img);
        facta_plus_img = getActivity().findViewById(R.id.facta_plus_img);
        past_action_plus_img = getActivity().findViewById(R.id.past_action_plus_img);
        authorised_plus_img = getActivity().findViewById(R.id.authorised_plus_img);
        standing_instruction_plus_img = getActivity().findViewById(R.id.standing_instruction_plus_img);
        nomination_plus_img = getActivity().findViewById(R.id.nomination_plus_img);
        nomination_plus_img_new = getActivity().findViewById(R.id.nomination_plus_img_new);
        getLoan_plus_img = getActivity().findViewById(R.id.getLoan_plus_img);
        uploadCheque1layout = getActivity().findViewById(R.id.uploadCheque1layout);
        cheque1fBox1Image = getActivity().findViewById(R.id.cheque1fBox1Image);
        uploadCheque2Layout = getActivity().findViewById(R.id.uploadCheque2Layout);
        uploadCheque2Box2ImageTwo = getActivity().findViewById(R.id.uploadCheque2Box2ImageTwo);
        uploadBankLayout = getActivity().findViewById(R.id.uploadBankLayout);
        BankBox1Image = getActivity().findViewById(R.id.BankBox1Image);
        cancel_btn_getLoan_detail = getActivity().findViewById(R.id.cancel_btn_getLoan_detail);


        if (AppInfo.allStateNamesArrayList != null) {

            if(!true){
                stateArray = new String[AppInfo.allStateNamesArrayList.size()];
                stateIdArray = new String[AppInfo.allStateIdArrayList.size()];
                for (int i = 0; i < AppInfo.allStateNamesArrayList.size(); i++) {
                    stateArray[i] = AppInfo.allStateNamesArrayList.get(i);
                    stateIdArray[i] = AppInfo.allStateIdArrayList.get(i);
                }
            }else{

                stateArray = new String[AppInfo.allStateNamesArrayList.size() + 1];
                stateIdArray = new String[AppInfo.allStateIdArrayList.size() + 1];
                stateArray[0] = "Select State";
                stateIdArray[0] = "0";
                for (int i = 0; i < AppInfo.allStateNamesArrayList.size(); i++) {
                    stateArray[i + 1] = AppInfo.allStateNamesArrayList.get(i);
                    stateIdArray[i + 1] = AppInfo.allStateIdArrayList.get(i);
                }
            }
        }

        txt_gurdian_city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityArray != null) {
                    final Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                    final ListView listView = view1.findViewById(R.id.listView);
                    SearchView searchView = view1.findViewById(R.id.searchView);
                    searchView.requestFocus();
                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
                    listView.setAdapter(stringArrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();

                            String selectedCity = String.valueOf(listView.getItemAtPosition(i));
                            txt_gurdian_city2.setText(selectedCity);
                            //isCitySelected = true;
                        }
                    });

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String newText) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            stringArrayAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    dialog.setContentView(view1);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_LONG).show();
                }
            }
        });

        txt_gurdian_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityArray != null) {
                    final Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                    final ListView listView = view1.findViewById(R.id.listView);
                    SearchView searchView = view1.findViewById(R.id.searchView);
                    searchView.requestFocus();
                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
                    listView.setAdapter(stringArrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();
                            String selectedCity = String.valueOf(listView.getItemAtPosition(i));
                            txt_gurdian_city.setText(selectedCity);
                            //isCitySelected = true;
                        }
                    });

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String newText) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            stringArrayAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    dialog.setContentView(view1);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_LONG).show();
                }
            }
        });
        addcity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityArray != null) {
                    final Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                    final ListView listView = view1.findViewById(R.id.listView);
                    SearchView searchView = view1.findViewById(R.id.searchView);
                    searchView.requestFocus();
                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
                    listView.setAdapter(stringArrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();
                            String selectedCity = String.valueOf(listView.getItemAtPosition(i));
                            addcity3.setText(selectedCity);
                            //isCitySelected = true;
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String newText) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            stringArrayAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    dialog.setContentView(view1);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_LONG).show();
                }
            }
        });
        addcity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityArray != null) {
                    final Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                    final ListView listView = view1.findViewById(R.id.listView);
                    SearchView searchView = view1.findViewById(R.id.searchView);
                    searchView.requestFocus();
                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
                    listView.setAdapter(stringArrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();
                            String selectedCity = String.valueOf(listView.getItemAtPosition(i));
                            addcity2.setText(selectedCity);
                            //isCitySelected = true;
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String newText) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            stringArrayAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    dialog.setContentView(view1);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_LONG).show();
                }
            }
        });




        addcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityArray != null) {
                    final Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                    final ListView listView = view1.findViewById(R.id.listView);
                    SearchView searchView = view1.findViewById(R.id.searchView);
                    searchView.clearFocus();
                    //searchView.requestFocus();
                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
                    listView.setAdapter(stringArrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();

                            String selectedCity = String.valueOf(listView.getItemAtPosition(i));
                            addcity.setText(selectedCity);
                            //isCitySelected = true;
                        }
                    });

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String newText) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            stringArrayAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    dialog.setContentView(view1);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_LONG).show();
                }
            }
        });
        txt_gurdian_state3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.search_dialog, null);
                final ListView listView = view1.findViewById(R.id.listView);
                SearchView searchView = view1.findViewById(R.id.searchView);
                searchView.clearFocus();
                //searchView.requestFocus();

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        stateArray);
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                        Log.e("Selected State", selectedFromList);
                        dialog.dismiss();
                        txt_gurdian_state3.setText(selectedFromList);
                        if (!txt_gurdian_state3.toString().isEmpty()) {
                            NewAddressScreen.getCityByStateName(selectedFromList, getActivity());
                            //getCityByStateName(selectedFromList, getActivity());
                        }
                        txt_gurdian_city3.setText("");

                        if(selectedFromList.equals("Select State")) {
                            cityArray = null;
                            //isCitySelected = false;
                            //isStateSelected = false;

                        }else{
                            //isStateSelected = true;
                            if(txt_gurdian_state3.getText().toString().equals(selectedStateName)) {
                            }else{
                                // isCitySelected = false;
                            }
                            getCityByStateName(selectedFromList, getActivity());
                        } }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stringArrayAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                dialog.setContentView(view1);
                dialog.show();
            }
        });

        txt_gurdian_state2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.search_dialog, null);
                final ListView listView = view1.findViewById(R.id.listView);
                SearchView searchView = view1.findViewById(R.id.searchView);
                searchView.clearFocus();
                //searchView.requestFocus();

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        stateArray);
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                        Log.e("Selected State", selectedFromList);
                        dialog.dismiss();
                        txt_gurdian_state2.setText(selectedFromList);
                        if (!txt_gurdian_state2.toString().isEmpty()) {
                            NewAddressScreen.getCityByStateName(selectedFromList, getActivity());
                            //getCityByStateName(selectedFromList, getActivity());
                        }
                        txt_gurdian_city2.setText("");

                        if(selectedFromList.equals("Select State")) {
                            cityArray = null;
                            //isCitySelected = false;
                            //isStateSelected = false;

                        }else{
                            //isStateSelected = true;
                            if(txt_gurdian_state2.getText().toString().equals(selectedStateName)) {
                            }else{
                                // isCitySelected = false;
                            }
                            getCityByStateName(selectedFromList, getActivity());
                        } }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stringArrayAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                dialog.setContentView(view1);
                dialog.show();
            }
        });

        txt_gurdian_city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityArray != null) {
                    final Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.search_dialog, null);
                    final ListView listView = view1.findViewById(R.id.listView);
                    SearchView searchView = view1.findViewById(R.id.searchView);
                    searchView.requestFocus();
                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityArray);
                    listView.setAdapter(stringArrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();
                            String selectedCity = String.valueOf(listView.getItemAtPosition(i));
                            txt_gurdian_city3.setText(selectedCity);
                            //isCitySelected = true;
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String newText) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            stringArrayAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    dialog.setContentView(view1);
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "Please select state first", Toast.LENGTH_LONG).show();
                }
            }
        });

        txt_gurdian_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.search_dialog, null);
                final ListView listView = view1.findViewById(R.id.listView);
                SearchView searchView = view1.findViewById(R.id.searchView);
                searchView.clearFocus();
                //searchView.requestFocus();

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        stateArray);
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                        Log.e("Selected State", selectedFromList);
                        dialog.dismiss();
                        txt_gurdian_state.setText(selectedFromList);
                        if (!txt_gurdian_state.toString().isEmpty()) {
                            NewAddressScreen.getCityByStateName(selectedFromList, getActivity());
                            //getCityByStateName(selectedFromList, getActivity());
                        }
                        addcity.setText("");
                        if(selectedFromList.equals("Select State")) {
                            cityArray = null;
                            //isCitySelected = false;
                            //isStateSelected = false;

                        }else{
                            //isStateSelected = true;
                            if(txt_gurdian_state.getText().toString().equals(selectedStateName)) {
                            }else{
                                // isCitySelected = false;
                            }
                            getCityByStateName(selectedFromList, getActivity());
                        } }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stringArrayAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                dialog.setContentView(view1);
                dialog.show();
            }
        });
        addstate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.search_dialog, null);
                final ListView listView = view1.findViewById(R.id.listView);
                SearchView searchView = view1.findViewById(R.id.searchView);
                searchView.clearFocus();
                //searchView.requestFocus();

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        stateArray);
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                        Log.e("Selected State", selectedFromList);
                        dialog.dismiss();
                        addstate3.setText(selectedFromList);
                        if (!addstate3.toString().isEmpty()) {
                            NewAddressScreen.getCityByStateName(selectedFromList, getActivity());
                            //getCityByStateName(selectedFromList, getActivity());
                        }
                        addcity3.setText("");
                        if(selectedFromList.equals("Select State")) {
                            cityArray = null;
                            //isCitySelected = false;
                            //isStateSelected = false;
                        }else{
                            //isStateSelected = true;
                            if(addstate2.getText().toString().equals(selectedStateName)) {

                            }else{
                                //isCitySelected = false;
                            }
                            getCityByStateName(selectedFromList, getActivity());
                        } }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stringArrayAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                dialog.setContentView(view1);
                dialog.show();
            }
        });

        addstate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.search_dialog, null);
                final ListView listView = view1.findViewById(R.id.listView);
                SearchView searchView = view1.findViewById(R.id.searchView);
                searchView.clearFocus();
                //searchView.requestFocus();

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        stateArray);
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                        Log.e("Selected State", selectedFromList);
                        dialog.dismiss();
                        addstate2.setText(selectedFromList);
                        if (!addstate2.toString().isEmpty()) {
                            NewAddressScreen.getCityByStateName(selectedFromList, getActivity());
                            //getCityByStateName(selectedFromList, getActivity());
                        }
                        addcity2.setText("");
                        if(selectedFromList.equals("Select State")) {
                            cityArray = null;
                            //isCitySelected = false;
                            //isStateSelected = false;
                        }else{
                            //isStateSelected = true;
                            if(addstate2.getText().toString().equals(selectedStateName)) {

                            }else{
                                //isCitySelected = false;
                            }
                            getCityByStateName(selectedFromList, getActivity());
                        } }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stringArrayAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                dialog.setContentView(view1);
                dialog.show();
            }
        });

        addstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.search_dialog, null);
                final ListView listView = view1.findViewById(R.id.listView);
                SearchView searchView = view1.findViewById(R.id.searchView);
                searchView.clearFocus();
                //searchView.requestFocus();

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        stateArray);
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedFromList = String.valueOf(listView.getItemAtPosition(i));
                        Log.e("Selected State", selectedFromList);
                        dialog.dismiss();
                        addstate.setText(selectedFromList);
                        if (!addstate.toString().isEmpty()) {
                            NewAddressScreen.getCityByStateName(selectedFromList, getActivity());
                            //getCityByStateName(selectedFromList, getActivity());
                        }
                        addcity.setText("");
                        if(selectedFromList.equals("Select State")) {
                            cityArray = null;
                            //isCitySelected = false;
                            // isStateSelected = false;
                        }else{
                            //isStateSelected = true;
                            if(addstate.getText().toString().equals(selectedStateName)) {
                            }else{
                                // isCitySelected = false;
                            }
                            getCityByStateName(selectedFromList, getActivity());
                        } }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stringArrayAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                dialog.setContentView(view1);
                dialog.show();
            }
        });


        nomination_plus_img_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ReviewFragment : " + " add_nominee : on click ");
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.nominee_yes, null);
                nominationsLayout_new.addView(rowView, nominationsLayout_new.getChildCount());
            }
        });

        gurdianaddresscheckbox_ofsecondnominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    gurdiansaddressselcted=true;
                    gurdiansaddressselcted_ofsecondnominee=true;
                    EkycAddressDetails_ifsave();
                    gurdian_address2.setVisibility(View.GONE);

                }else{
                    gurdian_address2.setVisibility(View.VISIBLE);
                    gurdiansaddressselcted_ofsecondnominee=false;
                    edittextpincode2.setText("");
                    edt_gurdianaddress2.setText("");
                    edt_gurdianaddress_two2.setText("");
                    edt_gurdianaddress_three2.setText("");
                    txt_gurdian_city2.setText("");
                    txt_gurdian_state2.setText("");

                }}

            private void EkycAddressDetails_ifsave() {
                HashMap<String, Object> parameters;
                parameters = new HashMap<String, Object>();
                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.EkycAddressDetails,
                        "EkycAddressDetails",
                        parameters, "POST");

            }
        });


        gurdianaddresscheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    System.out.println("Checked");
                    gurdiansaddressselcted=true;
                    gurdian_address1.setVisibility(View.GONE);
                    EkycAddressDetails_ifsave();
                }else{
                    gurdian_address1.setVisibility(View.VISIBLE);
                    gurdiansaddressselcted=false;
                    editgurdianpincode.setText("");
                    edt_gurdianaddress_one.setText("");
                    edt_gurdianaddress_two.setText("");
                    edt_gurdianaddress_three.setText("");
                    txt_gurdian_city.setText("");
                    txt_gurdian_state.setText("");
                }}

            private void EkycAddressDetails_ifsave() {
                HashMap<String, Object> parameters;
                parameters = new HashMap<String, Object>();
                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.EkycAddressDetails,
                        "EkycAddressDetails",
                        parameters, "POST");
            }});

        checkBoxgurdianaddress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    System.out.println("Checked");
                    EkycAddressDetails_ifsave();
                    gurdiansaddressselcted_ofthirdnominee=true;
                    gurdian_address3.setVisibility(View.GONE);

                }else{
                    gurdian_address3.setVisibility(View.VISIBLE);
                    addpincode3.setText("");
                    edt_gurdianaddress_one3.setText("");
                    edt_gurdianaddress_two3.setText("");
                    edt_gurdianaddress_three3.setText("");
                    txt_gurdian_city3.setText("");
                    txt_gurdian_state3.setText("");
                    gurdiansaddressselcted_ofthirdnominee=false;
                }
            }
            private void EkycAddressDetails_ifsave() {
                HashMap<String, Object> parameters;
                parameters = new HashMap<String, Object>();
                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.EkycAddressDetails,
                        "EkycAddressDetails",
                        parameters, "POST");

            }
        });


        checkBoxaddress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    System.out.println("Checked");
                    EkycAddressDetails_ifsave();
                    third_Nominee_Address=true;
                    nominee_address_different_news3.setVisibility(View.GONE);

                }else{
                    nominee_address_different_news3.setVisibility(View.VISIBLE);
                    addpincode3.setText("");
                    addone3.setText("");
                    addtwo3.setText("");
                    addthree3.setText("");
                    addcity3.setText("");
                    addstate3.setText("");
                    System.out.println("Un-Checked");
                    third_Nominee_Address=false;
                }
            }
            private void EkycAddressDetails_ifsave() {
                HashMap<String, Object> parameters;
                parameters = new HashMap<String, Object>();
                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.EkycAddressDetails,
                        "EkycAddressDetails",
                        parameters, "POST");

            }
        });

        checkBoxaddress_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {

                    System.out.println("Checked");

                    EkycAddressDetails_ifsave();
                    secondn_Nominee_Address=true;
                    nominee_address_different_news2.setVisibility(View.GONE);


                }else{
                    nominee_address_different_news2.setVisibility(View.VISIBLE);
                    secondn_Nominee_Address=false;
                    addpincode2.setText("");
                    addres_one_of_nominee2.setText("");
                    addres_two_of_nominee2.setText("");
                    addres_three_of_nominee2.setText("");
                    addcity2.setText("");
                    addstate2.setText("");
                    System.out.println("Un-Checked");
                }
            }

            private void EkycAddressDetails_ifsave() {
                HashMap<String, Object> parameters;
                parameters = new HashMap<String, Object>();
                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.EkycAddressDetails,
                        "EkycAddressDetails",
                        parameters, "POST");
            }
        });

        checkBoxaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    System.out.println("Checked");
                    EkycAddressDetails_ifsave();
                    first_Nominee_Address=true;
                    nominee_address_different_news.setVisibility(View.GONE);

                }else{
                    first_Nominee_Address=false;
                    nominee_address_different_news.setVisibility(View.VISIBLE);
                    addpincode.setText("");
                    addone.setText("");
                    addtwo.setText("");
                    addthree.setText("");
                    addcity.setText("");
                    addstate.setText("");
                    System.out.println("Un-Checked");
                }
            }

            private void EkycAddressDetails_ifsave() {
                HashMap<String, Object> parameters;
                parameters = new HashMap<String, Object>();
                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.EkycAddressDetails,
                        "EkycAddressDetails",
                        parameters, "POST");

            }
        });
        uploadCheque1layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleDoc = "Cheque One";
                pickupImageNew("Cheque One");
            }
        });

        uploadCheque2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleDoc = "Cheque Two";
                pickupImageNew("Cheque Two");

            }
        });

        uploadBankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleDoc = "Bank Statement";
                pickupImageNew("Bank Statement");
            }
        });


        /*******************************************************************************************/

        ChequeNoFirstLayout = getActivity().findViewById(R.id.ChequeNoFirstLayout);
        etNoteHedFirst = getActivity().findViewById(R.id.etNoteHedFirst);
        etNoteHedSecond = getActivity().findViewById(R.id.etNoteHedSecond);
        etNoteHedLayout = getActivity().findViewById(R.id.etNoteHedLayout);
        doclayout = getActivity().findViewById(R.id.doclayout);
        BranchFirstLayout = getActivity().findViewById(R.id.BranchFirstLayout);
        etBankLayout = getActivity().findViewById(R.id.etBankLayout);
        ChequeNoSecondtLayout = getActivity().findViewById(R.id.ChequeNoSecondtLayout);
        etBankLayoutSecond = getActivity().findViewById(R.id.etBankLayoutSecond);
        BranchSecondLayout = getActivity().findViewById(R.id.BranchSecondLayout);
        etChequeFirst = getActivity().findViewById(R.id.etChequeNoFirst);
        etBankFirst = getActivity().findViewById(R.id.etBankFirst);
        etBranchFirst = getActivity().findViewById(R.id.etBranchFirst);
        etchequeSecond = getActivity().findViewById(R.id.etChequeNoSecond);
        loan_validation_error_txt = getActivity().findViewById(R.id.loan_validation_error_txt);
        loan_validation_error_txt_tenure = getActivity().findViewById(R.id.loan_validation_error_txt_tenure);
        etBankSecond = getActivity().findViewById(R.id.etBankSecond);
        etBranchSecond = getActivity().findViewById(R.id.etBranchSecond);
        etLoanAmount = getActivity().findViewById(R.id.etLoanAmount);
        etLoanTenurePeriod = getActivity().findViewById(R.id.etLoanTenurePeriod);
        getLoanLayout = getActivity().findViewById(R.id.getLoanLayout);
        Log.d("LOanAmount", loanAmount + "=====");
        //  etLoanAmount.setFilters( new InputFilter[]{ new MinMaxFilter( "12" , "36" )}) ;


        etLoanAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("ReviewFragment : ", "afterTextChanged : editable " + editable);


                if (editable.length() > 0) {
                    int input = Integer.parseInt(editable.toString());
                    int loanAmountt = Integer.parseInt(loanAmount.toString());

                    Log.d("ReviewFragment : ", "afterTextChanged : editable " + editable);
                    if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                        Log.d("ReviewFragment : ", "afterTextChanged : input " + input);

                        if (input < 10000 || input > 1000000) {
                            loan_validation_error_txt.setVisibility(View.VISIBLE);
                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                            submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
                            submit_btn_getLoan_detail.setEnabled(false);
                            Log.d("ReviewFragment : ", "afterTextChanged : input bw 10000 to 1000000 ");


                        } else {
                            loan_validation_error_txt.setVisibility(View.GONE);
                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                            submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                            submit_btn_getLoan_detail.setEnabled(true);

                        }
                    }

                    if (input > 250000) {

                        ChequeNoFirstLayout.setVisibility(View.VISIBLE);
                        etNoteHedSecond.setVisibility(View.VISIBLE);
                        etNoteHedFirst.setVisibility(View.VISIBLE);
                        etNoteHedLayout.setVisibility(View.VISIBLE);
                        ChequeNoSecondtLayout.setVisibility(View.VISIBLE);
                        BranchFirstLayout.setVisibility(View.VISIBLE);
                        BranchSecondLayout.setVisibility(View.VISIBLE);
                        etBankLayout.setVisibility(View.VISIBLE);
                        etBankLayoutSecond.setVisibility(View.VISIBLE);
                        doclayout.setVisibility(View.VISIBLE);
                        getLoanLayout.setVisibility(View.VISIBLE);
                        getLoanDetailsButtonvalidation();

                        Log.d("ReviewFragment : ", "afterTextChanged :input > 250000 " + input);


                        if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                            submit_btn_additional_detail.setVisibility(View.GONE);
                        } else {
                            submit_btn_additional_detail.setVisibility(View.VISIBLE);
                        }

                        if (input > 10000 && input < 1000000 && etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 && etNoteHedLayout.getVisibility() == View.GONE) {
                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                            submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                            submit_btn_getLoan_detail.setEnabled(true);

                            Log.d("ReviewFragment : ", "afterTextChanged :input > 10000 && input < 1000000  and etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 " + input + " length : " + etLoanTenurePeriod.getText().length());

                        } else {
                            Log.d("ReviewFragment : ", "afterTextChanged :input > 10000 && input < 1000000  and etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3  else " + input);

                            if (input > 10000 && input < 1000000 && etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 && etNoteHedLayout.getVisibility() == View.VISIBLE) {

                                Log.d("ReviewFragment : ", "afterTextChanged :input > 10000 && input < 1000000  and etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 else if " + input + " length : " + etLoanTenurePeriod.getText().length());

                                getLoanDetailsButtonvalidation();
                            } else {

                                Log.d("ReviewFragment : ", "afterTextChanged :input > 10000 && input < 1000000  and etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 else else  " + input + " length : " + etLoanTenurePeriod.getText().length());

                                submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                                submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
                                submit_btn_getLoan_detail.setEnabled(false);
                            }
                        }
                    } else {
                        Log.d("ReviewFragment : ", "afterTextChanged :input > 2500000   " + input);

                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                        submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                        submit_btn_getLoan_detail.setEnabled(true);
                        etNoteHedLayout.setVisibility(View.GONE);
                        ChequeNoFirstLayout.setVisibility(View.GONE);
                        ChequeNoSecondtLayout.setVisibility(View.GONE);
                        etNoteHedFirst.setVisibility(View.GONE);
                        etNoteHedSecond.setVisibility(View.GONE);
                        BranchFirstLayout.setVisibility(View.GONE);
                        BranchSecondLayout.setVisibility(View.GONE);
                        etBankLayout.setVisibility(View.GONE);
                        etBankLayoutSecond.setVisibility(View.GONE);
                        doclayout.setVisibility(View.GONE);
                        getLoanLayout.setVisibility(View.VISIBLE);
                        getLoanDetailsButtonvalidation();

                        if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {

                            submit_btn_additional_detail.setVisibility(View.GONE);

                        } else {

                            submit_btn_additional_detail.setVisibility(View.VISIBLE);
                        }

                        if (input > 9999 && input < 1000001 && etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 && etNoteHedLayout.getVisibility() == View.GONE) {

                            Log.d("ReviewFragment : ", "afterTextChanged :input > 9999 && input < 1000001 and  etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3  " + input + " length : " + etLoanTenurePeriod.getText().length());

                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                            submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                            submit_btn_getLoan_detail.setEnabled(true);

                        } else {

                            Log.d("ReviewFragment : ", "afterTextChanged : else  " + input + " length : " + etLoanTenurePeriod.getText().length());
                            if (input > 9999 && input < 1000001 && etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3 && etNoteHedLayout.getVisibility() == View.VISIBLE) {
                                Log.d("ReviewFragment : ", "afterTextChanged else if :input > 9999 && input < 1000001 and  etLoanTenurePeriod.getText().length() > 1 && etLoanTenurePeriod.getText().length() < 3  " + input + " length : " + etLoanTenurePeriod.getText().length());

                                getLoanDetailsButtonvalidation();
                            } else {
                                submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                                submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
                                submit_btn_getLoan_detail.setEnabled(false);
                            }

                        }

//                        if (editable.length() > 4 && editable.length() < 8 && etLoanTenurePeriod.getText().length() > 1 || etLoanTenurePeriod.getText().length() < 3 ){
//                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
//                            submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
//                            submit_btn_getLoan_detail.setEnabled(true);
//                        } else {
//                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
//                            submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
//                            submit_btn_getLoan_detail.setEnabled(false);
//
//                        }
                    }
                }
//                if (editable.length() > 0){
//                    isLoanAmountFilled = true;
//                    getLoanDetailsButtonvalidation();
//                }else {
//                    getLoanDetailsButtonvalidation();
//                    isLoanAmountFilled = false;
//                }
            }
        });
        etLoanTenurePeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    int input;
                    if (!editable.toString().equals("null")) {
                        input = Integer.parseInt(editable.toString());
                        if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                            if (input < 12 || input > 36) {
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Loan Tenure minimum 12 months & maximum 36 months")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                loan_validation_error_txt_tenure.setVisibility(View.VISIBLE);
                                submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                                submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
                                submit_btn_getLoan_detail.setEnabled(false);
                            } else {
                                loan_validation_error_txt_tenure.setVisibility(View.GONE);

                                submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                                submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                                submit_btn_getLoan_detail.setEnabled(true);
                            }
                        }
                    } else {
                        etLoanTenurePeriod.setText("");

                    }


                }
                if (editable.length() > 0) {
                    isLoanTenureFilled = true;
                    int input = Integer.parseInt(editable.toString());
                    //int loanAmountt = Integer.parseInt(loanAmount.toString());
                    if (input > 11 && input < 37 && etLoanAmount.getText().length() > 3 && etNoteHedLayout.getVisibility() == View.GONE) {
                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                        submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
                        submit_btn_getLoan_detail.setEnabled(true);
                    } else {

                        if (input > 11 && input < 37 && etLoanAmount.getText().length() > 3 && etNoteHedLayout.getVisibility() == View.VISIBLE) {
                            getLoanDetailsButtonvalidation();
                        } else {
                            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
                            submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
                            submit_btn_getLoan_detail.setEnabled(false);
                        }

                    }

//                    if (editable.length() == 2 && etLoanAmount.getText().length() > 4){
//
//                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
//                        submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
//                        submit_btn_getLoan_detail.setEnabled(true);
//                    }else {
//                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
//                        submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
//                        submit_btn_getLoan_detail.setEnabled(false);
//
//                    }

                } else {
                    isLoanTenureFilled = false;
                    getLoanDetailsButtonvalidation();
//                    if (editable.length() == 2 && etLoanAmount.getText().length() > 4){
//
//                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
//                        submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
//                        submit_btn_getLoan_detail.setEnabled(true);
//                    }else {
//                        submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
//                        submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
//                        submit_btn_getLoan_detail.setEnabled(false);
//
//                    }
                }

            }
        });

        etChequeFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    isChequeFirstFilled = true;
                    getLoanDetailsButtonvalidation();
                } else {
                    isChequeFirstFilled = false;
                    getLoanDetailsButtonvalidation();
                }

            }
        });

        etBankFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) {
                    if (editable.toString().length() == 1) {
                        if (editable.toString().equals(" "))
                            etBankFirst.setText("");
                    } else {
                        isBankFirstFiiled = true;
                        getLoanDetailsButtonvalidation();
                    }
                } else {
                    isBankFirstFiiled = false;
                    getLoanDetailsButtonvalidation();
                }


            }
        });
        etBranchFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {

                    if (editable.toString().length() == 1) {
                        if (editable.toString().equals(" "))
                            etBranchFirst.setText("");
                    } else {
                        isBranchFirstFilled = true;
                        getLoanDetailsButtonvalidation();
                    }
                } else {
                    isBranchFirstFilled = false;
                    getLoanDetailsButtonvalidation();
                }
            }
        });
        etBranchSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {

                    if (editable.toString().length() == 1) {
                        if (editable.toString().equals(" "))
                            etBranchSecond.setText("");
                    } else {
                        isBranchSecondfilled = true;
                        getLoanDetailsButtonvalidation();
                    }
                } else {
                    isBranchSecondfilled = false;
                    getLoanDetailsButtonvalidation();
                }
            }
        });
        etchequeSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    isChequeSecondFilled = true;
                    getLoanDetailsButtonvalidation();
                } else {
                    isChequeSecondFilled = false;
                    getLoanDetailsButtonvalidation();
                }
            }
        });
        etBankSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    if (editable.toString().length() == 1) {
                        if (editable.toString().equals(" "))
                            etBankSecond.setText("");
                    } else {
                        isBankSecondfilled = true;
                        getLoanDetailsButtonvalidation();
                    }
                } else {
                    isBankSecondfilled = false;
                    getLoanDetailsButtonvalidation();
                }
            }
        });


        submit_btn_getLoan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parameters = new HashMap<String, Object>();
                parameters.put("AdditionalDetailId", additionalDetailsIdd != null ? additionalDetailsIdd : 0);
                parameters.put("LoanAmount", etLoanAmount.getText().toString() + "");
                parameters.put("LoanTenurePeriod", etLoanTenurePeriod.getText().toString() + "");
                parameters.put("PDC_UDC_Number", pdc_udc_number != null ? pdc_udc_number + "" : 0);
                parameters.put("ChequeNo_first", etChequeFirst.getText().toString() != null ? etChequeFirst.getText().toString() + "" : "");
                parameters.put("Bank_first", etBankFirst.getText().toString() != null ? etBankFirst.getText().toString() + "" : "");
                parameters.put("Branch_first", etBranchFirst.getText().toString() != null ? etBranchFirst.getText().toString() + "" : "");
                parameters.put("ChequeNo_second", etchequeSecond.getText().toString() != null ? etchequeSecond.getText().toString() + "" : "");
                parameters.put("Bank_second", etBankSecond.getText().toString() != null ? etBankSecond.getText().toString() + "" : "");
                parameters.put("Branch_second", etBranchSecond.getText().toString() != null ? etBranchSecond.getText().toString() + "" : "");
                getRes.getResponseFromURL(WebServices.getBaseURL +
                                WebServices.updateLoanDetails,
                        "updateLoanDetails",
                        parameters);


            }
        });

        cancel_btn_getLoan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api/GetLoan/DeleteLoanDoc?EkycId=125'
                Log.d("myIdDElete", AppInfo.ekycApplicationId + "---9999999999");
                getRes.getResponse(WebServices.getBaseURL +
                                WebServices.GetLoanDeleteLoanDoc + "?EkycId=" + eKycId,
                        "GetLoanDeleteLoanDoc",
                        null);
            }
        });


        /*******************************************************************************************/

        ll_account_plus_img = getActivity().findViewById(R.id.ll_account_plus_img);


        account_bottom = getActivity().findViewById(R.id.account_bottom);
        facta_bottom = getActivity().findViewById(R.id.facta_bottom);
        past_action_bottom = getActivity().findViewById(R.id.past_action_bottom);
        authorisedPerson_bottom = getActivity().findViewById(R.id.authorisedPerson_bottom);
        standing_instructiom_bottom = getActivity().findViewById(R.id.standing_instructiom_bottom);
        nomination_bottom = getActivity().findViewById(R.id.nomination_bottom);
        nomination_bottom_new = getActivity().findViewById(R.id.nomination_bottom_new);
        getLoan_d_bottom = getActivity().findViewById(R.id.getLoan_bottom);

        if(getLoan_d_bottom.getVisibility() == View.VISIBLE) {
            submit_btn_additional_detail.setVisibility(View.GONE);
        } else{
            submit_btn_additional_detail.setVisibility(View.VISIBLE);
        }
        review_applicationMainLayout.setVisibility(View.VISIBLE);
        error_with_aadhar.setVisibility(View.GONE);
        congratulationsLayout.setVisibility(View.GONE);
        aadhar_not_linked.setVisibility(View.GONE);
        //additional_doc.setVisibility(View.GONE);
        reviewWebView.setVisibility(View.GONE);
        callAddtionalData();
        if (AppInfo.IsReadonlyMode) {

            userPanTextView.setVisibility(View.INVISIBLE);
            llSAveBrokrage.setVisibility(View.GONE);
            userFNameTextView.setVisibility(View.INVISIBLE);
            userClientCode_pencil.setVisibility(View.INVISIBLE);
            userMNameTextView.setVisibility(View.INVISIBLE);
            addressText.setVisibility(View.INVISIBLE);
            userPanTextView.setVisibility(View.INVISIBLE);
            userNameTextView.setVisibility(View.INVISIBLE);
            userEmailText.setVisibility(View.INVISIBLE);
            userMobileText.setVisibility(View.INVISIBLE);
            userMNameTextView.setVisibility(View.INVISIBLE);
            userMaritalStatusTextView.setVisibility(View.INVISIBLE);
            userGenderTextView.setVisibility(View.INVISIBLE);
            userAddressTextview.setVisibility(View.INVISIBLE);
            userPolicalPartyTextView.setVisibility(View.INVISIBLE);
            userCityTextView.setVisibility(View.INVISIBLE);
            userStateTextView.setVisibility(View.INVISIBLE);
            userIncomeTextView.setVisibility(View.INVISIBLE);
            userOccupationTextView.setVisibility(View.INVISIBLE);

            userTradingTextView.setVisibility(View.INVISIBLE);
            addressEdit.setVisibility(View.INVISIBLE);
            userDematTextView.setVisibility(View.INVISIBLE);
            userSegmentsTextView.setVisibility(View.INVISIBLE);
            usermtfTextView.setVisibility(View.INVISIBLE);
            userDOBTextView.setVisibility(View.INVISIBLE);
            EdtAdditionText.setVisibility(View.INVISIBLE);
            userPANText.setVisibility(View.INVISIBLE);
            userEditIncomeText.setVisibility(View.INVISIBLE);
            userIncomeText.setVisibility(View.INVISIBLE);
            userBankText.setVisibility(View.INVISIBLE);
            userSelfieText.setVisibility(View.INVISIBLE);
            userSignatureText.setVisibility(View.INVISIBLE);
            userVarificationText.setVisibility(View.INVISIBLE);
            userPostcalCodeTextView.setVisibility(View.INVISIBLE);

            if (currentStatu != null) {

                System.out.println("currentStatu" + currentStatu);
                Log.d("reviewFragment ", "currentStatu " + currentStatu);
                if (!currentStatu.equals("")) {
                    if (currentStatu.equals("PendingEsign")) {

                        ll_reviewSubmit.setVisibility(View.VISIBLE);

                    } else if (currentStatu.equals("Pending Authorization")) {

                        ll_reviewSubmit.setVisibility(View.GONE);
                        EdtAdditionText.setVisibility(View.INVISIBLE);
                        userBankText.setVisibility(View.INVISIBLE);

                    } else if (currentStatu.equals("Authorized")) {

                        ll_reviewSubmit.setVisibility(View.GONE);
                        EdtAdditionText.setVisibility(View.INVISIBLE);
                        userBankText.setVisibility(View.INVISIBLE);

                    } else if (currentStatu.equals("In Progress")) {

                        ll_reviewSubmit.setVisibility(View.VISIBLE);
                    } else if (currentStatu.equals("Review")) {
                        ll_reviewSubmit.setVisibility(View.GONE);
                        EdtAdditionText.setVisibility(View.INVISIBLE);
                        userBankText.setVisibility(View.INVISIBLE);
                    } else if (currentStatu.equals("ReadyToDispatch")) {
                        ll_reviewSubmit.setVisibility(View.VISIBLE);
                    } else if (currentStatu.equals("Rejected")) {
                        ll_reviewSubmit.setVisibility(View.VISIBLE);
                    } else {
                        ll_reviewSubmit.setVisibility(View.GONE);
                        EdtAdditionText.setVisibility(View.INVISIBLE);
                        userBankText.setVisibility(View.INVISIBLE);
                    }
                }
            }

        } else {
            userPanTextView.setVisibility(View.VISIBLE);
            llSAveBrokrage.setVisibility(View.VISIBLE);
            userFNameTextView.setVisibility(View.VISIBLE);
            userClientCode_pencil.setVisibility(View.VISIBLE);
            userMNameTextView.setVisibility(View.VISIBLE);
            userPanTextView.setVisibility(View.VISIBLE);
            userNameTextView.setVisibility(View.VISIBLE);
            userEmailText.setVisibility(View.GONE);
            userMobileText.setVisibility(View.GONE);
            userMNameTextView.setVisibility(View.VISIBLE);
            userMaritalStatusTextView.setVisibility(View.VISIBLE);
            userGenderTextView.setVisibility(View.VISIBLE);
            userAddressTextview.setVisibility(View.VISIBLE);
            ll_reviewSubmit.setVisibility(View.VISIBLE);
            addressText.setVisibility(View.VISIBLE);
            userCityTextView.setVisibility(View.VISIBLE);
            userStateTextView.setVisibility(View.VISIBLE);
            userIncomeTextView.setVisibility(View.VISIBLE);
            userOccupationTextView.setVisibility(View.VISIBLE);

            userTradingTextView.setVisibility(View.VISIBLE);
            userDematTextView.setVisibility(View.VISIBLE);
            userSegmentsTextView.setVisibility(View.VISIBLE);
            usermtfTextView.setVisibility(View.VISIBLE);
            userDOBTextView.setVisibility(View.VISIBLE);
            userPolicalPartyTextView.setVisibility(View.VISIBLE);
            EdtAdditionText.setVisibility(View.VISIBLE);
            userPANText.setVisibility(View.VISIBLE);
            userBankText.setVisibility(View.VISIBLE);
            userEditIncomeText.setVisibility(View.VISIBLE);
            userSelfieText.setVisibility(View.VISIBLE);
            userSignatureText.setVisibility(View.VISIBLE);
            userVarificationText.setVisibility(View.VISIBLE);
            userPostcalCodeTextView.setVisibility(View.VISIBLE);

            if (currentstatus != null) {
                String currentStatu = currentstatus;
                if (currentStatu.equals("ReadyToDispatch")) {
                    ll_reviewSubmit.setVisibility(View.VISIBLE);
                }
            }

        }

        llSAveBrokrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "Clicked");
                //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
                System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "AppInfo.IsReadonlyMode" + AppInfo.IsReadonlyMode);

                if (!AppInfo.IsReadonlyMode) {
                    System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " if AppInfo.IsReadonlyMode" + AppInfo.IsReadonlyMode);

                    System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "isLessThanRequired" + isLessThanRequired());
                    if (isLessThanRequired()) {
                        System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " if isLessThanRequired" + isLessThanRequired());

                        System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "documentUploaded" + documentUploaded);
                        if (documentUploaded) {
                            System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "if  documentUploaded" + documentUploaded);
                            try {
                                System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " turnOverEdit" + turnOverEdit.getText().toString());
                                if (turnOverEdit.getText().toString().length() == 0
                                        || Integer.parseInt(turnOverEdit.getText().toString()) == 0) {

                                    System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " if turnOverEdit" + turnOverEdit.getText().toString());


                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Values that you have entered are less than our plan for this you have to provide use the  additional detail at the bottom of the page.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                    brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                                    brokerageScroll.post(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                                        }
                                                    });
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                } else {

                                    System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " else turnOverEdit" + turnOverEdit.getText().toString());

                                    setBrokerage();
                                }
                            } catch (Exception e) {
                                System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "Exception" + e.getMessage());

                            }
                        } else {

                            System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "else documentUploaded" + documentUploaded);

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Please upload old contract note to save brokerage.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                            brokerageScroll.fullScroll(View.FOCUS_DOWN);

                                            brokerageScroll.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                                }
                                            });
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    } else {

                        System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " else isLessThanRequired" + isLessThanRequired());
                        setBrokerage();
                    }
                } else {
                    System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + " else AppInfo.IsReadonlyMode" + AppInfo.IsReadonlyMode);
                }
            }
        });

        varificationVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userDocuments userDocuments = FinalApplicationForm.getUserDocuments();
//                System.out.println("VERIFIVcc", ">>" + VideoUrl);
//                if(VideoUrl.length() > 0) {
//                    Intent tostart = new Intent(Intent.ACTION_VIEW);
//                    tostart.setDataAndType(Uri.parse(VideoUrl), "video/*");
//                    startActivity(tostart);
//                  }

                if (VideoExtension.equals("mp4")) {
                    if (VideoUrl.length() > 0) {
                        Intent tostart = new Intent(Intent.ACTION_VIEW);
                        tostart.setDataAndType(Uri.parse(VideoUrl), "video/*");
                        startActivity(tostart);
                    }
                } else if (VideoExtension.equals("jpg") || VideoExtension.equals("png") || VideoExtension.equals("jpeg")) {
                    if (VideoUrl.length() > 0) {
                        Intent i = new Intent(getActivity(),
                                FullScreenImageView.class);
                        i.putExtra("URL", VideoUrl);
                        startActivity(i);
                    }

                } else {
                }
            }
        });

        userAadharFrontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),
                        FullScreenImageView.class);
                i.putExtra("URL", ImgStrAadharFront);
                startActivity(i);
            }
        });
        userAadharBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),
                        FullScreenImageView.class);
                i.putExtra("URL", ImgStrAadharBack);
                startActivity(i);
            }
        });
        userPanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FullScreenImageView.class);
                i.putExtra("URL", ImgStrPAN);
                startActivity(i);
            }
        });

        userIncomeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("ReviewFragment : ", "in userIncomeImage click");
                Log.d("ReviewFragment : ", "ImgStrIncome " + ImgStrIncome);

                if (ImgStrIncome != null) {
                    if (ImgStrIncome.contains(".pdf")) {
                        Log.d("ReviewFragment : ", " userIncomeImage contain pdf " + ImgStrIncome);

                   /* Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setType("application/pdf");
                    pdfOpenintent.setDataAndType(Uri.parse(ImgStrIncome), "application/pdf");
                    try {
                        Log.d("ReviewFragment : " , " userIncomeImage contain pdf in try" +ImgStrIncome);
                        startActivity(pdfOpenintent);
                    } catch (ActivityNotFoundException e)
                    {
                        Log.d("ReviewFragment : " , " userIncomeImage contain pdf in catch " +e);
                    }*/

                        Log.d("ReviewFragment : ", " userIncomeImage contain pdf in catch " + Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url=" + ImgStrIncome));
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url=" + ImgStrIncome));
                        startActivity(pdfOpenintent);

                    }else{
                        Log.d("ReviewFragment : ", " userIncomeImage not contain pdf " + ImgStrIncome);
                        Intent i = new Intent(getActivity(),FullScreenImageView.class);
                        i.putExtra("URL", ImgStrIncome);
                        startActivity(i);
                    }
                }

            }
        });

        userBankImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReviewFragment : ", "in userBankImage click");
                if (ImgStrBank1.contains(".pdf")) {
                  /*  Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setType("application/pdf");
                    pdfOpenintent.setDataAndType(Uri.parse(ImgStrBank1), "application/pdf");
                    try {
                        startActivity(pdfOpenintent);
                    }catch (ActivityNotFoundException e) {
                     }*/
                    Log.d("ReviewFragment : ", " userBankImage1 contain pdf " + ImgStrBank1);
                    Log.d("ReviewFragment : ", " userBankImage1 contain pdf in catch " + Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url=" + ImgStrBank1));
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url=" + ImgStrBank1));
                    startActivity(pdfOpenintent);
                }else{
                    Intent i = new Intent(getActivity(), FullScreenImageView.class);
                    i.putExtra("URL", ImgStrBank1);
                    startActivity(i);
                }}});

        userBankImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ImgStrBan2.contains(".pdf")) {
                    Log.d("ReviewFragment : ", " userBankImage2 contain pdf " + ImgStrBan2);
                    Log.d("ReviewFragment : ", " userBankImage2 contain pdf in catch " + Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url=" + ImgStrBan2));
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url=" + ImgStrBan2));
                    startActivity(pdfOpenintent);
                }else {
                    Intent i = new Intent(getActivity(), FullScreenImageView.class);
                    i.putExtra("URL", ImgStrBan2);
                    startActivity(i);
                }}
        });

        userPhotoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FullScreenImageView.class);
                i.putExtra("URL", ImgStrPhoto);
                startActivity(i);
            }
        });
        userSignatureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FullScreenImageView.class);
                i.putExtra("URL", ImgStrSignature);
                startActivity(i);
            }
        });


        additionalImageone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AddtioinalOnePDFurl.contains(".pdf")) {
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setType("application/pdf");
                    pdfOpenintent.setDataAndType(Uri.parse(AddtioinalOnePDFurl), "application/pdf");
                    try {
                        startActivity(pdfOpenintent);
                    } catch (ActivityNotFoundException e) {

                    }
                } else {
                    Intent i = new Intent(getActivity(), FullScreenImageView.class);
                    i.putExtra("URL", ImgStrAdditional1);
                    startActivity(i);
                }
            }
        });
        additionalImagetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ImgStrAdditional2.contains(".pdf")) {
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setType("application/pdf");
                    pdfOpenintent.setDataAndType(Uri.parse(ImgStrAdditional2), "application/pdf");
                    try {
                        startActivity(pdfOpenintent);

                    }catch (ActivityNotFoundException e) {

                    }
                } else {
                    Intent i = new Intent(getActivity(), FullScreenImageView.class);
                    i.putExtra("URL", ImgStrAdditional2);
                    startActivity(i);
                }
            }
        });


        discountMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                discountBrokerage.setVisibility(View.VISIBLE);
                premiumBrokerage.setVisibility(View.GONE);

                discountMenu.setBackgroundResource(R.mipmap.bottombtn);
                discountMenu.setTextColor(Color.parseColor("#ffffff"));

                premiumMenu.setBackgroundResource(R.drawable.gray_btn);
                premiumMenu.setTextColor(Color.parseColor("#A2A2A2"));
            }
        });


        nominee_layoutone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nominee_layoutone.setBackgroundResource(R.mipmap.bottombtn);
                nominee_layouttwo.setBackgroundResource(R.color.colorPrimaryDark);
                nominee_layoutthree.setBackgroundResource(R.color.colorPrimaryDark);
            }
        });
        nominee_layouttwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nominee_layoutone.setBackgroundResource(R.color.colorPrimaryDark);
                nominee_layouttwo.setBackgroundResource(R.mipmap.bottombtn);
                nominee_layoutthree.setBackgroundResource(R.color.colorPrimaryDark);

            }
        });
        nominee_layoutthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nominee_layoutone.setBackgroundResource(R.color.colorPrimaryDark);
                nominee_layouttwo.setBackgroundResource(R.color.colorPrimaryDark);
                nominee_layoutthree.setBackgroundResource(R.mipmap.bottombtn);


            }
        });


        premiumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                discountBrokerage.setVisibility(View.GONE);
                premiumBrokerage.setVisibility(View.VISIBLE);

                discountMenu.setBackgroundResource(R.drawable.gray_btn);
                discountMenu.setTextColor(Color.parseColor("#A2A2A2"));

                premiumMenu.setBackgroundResource(R.mipmap.bottombtn);
                premiumMenu.setTextColor(Color.parseColor("#ffffff"));
            }
        });
        minorToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleValue = "yes";
                    lMainGardian.setVisibility(View.VISIBLE);
                }else{
                    toggleValue = "no";
                    lMainGardian.setVisibility(View.GONE);
                }}});
        minorToggle_news.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    percentagesharingodnomineeone.setText("Percentage(%) Sharing Of Guardian one");
                    addressofnomineone.setText("Address of Guardian");
                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                    guardiandetailslayout.setVisibility(View.VISIBLE);
                    toggleValue = "yes";
                }else{
                    guardiandetailslayout.setVisibility(View.GONE);
                    toggleValue = "no";
                }}
        });

        minorToggle_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleValue = "yes";
                    lMainGardian_new.setVisibility(View.VISIBLE);
                    mainScrollview_new.fullScroll(ScrollView.FOCUS_UP);
                } else {
                    toggleValue = "no";
                    lMainGardian_new.setVisibility(View.GONE);
                }
            }
        });

        changeAdditionalDetail = getActivity().findViewById(R.id.changeAdditionalDetail);
        nominee = getActivity().findViewById(R.id.nominee);

        ll_reviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppInfo.IsMobileVerify) {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setCancelable(false)
                            .setTitle("Mobile Verification")
                            //.setMessage("For eSign App will download form it will take some time.")
                            .setMessage("Your Mobile Verification is incomplete, Please complete it to process.")
                            .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    builder.show();
                } else if (!AppInfo.IsEmailVerify) {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setCancelable(false)
                            .setTitle("E-Mail Verification")
                            //.setMessage("For eSign App will download form it will take some time.")
                            .setMessage("Your E-Mail Verification is incomplete, Please complete it to process.")
                            .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }else if (!AppInfo.IsMobileVerify && !AppInfo.IsEmailVerify) {
                    if (acProgressFlower != null) {
                        if (acProgressFlower.isShowing()) {
                            acProgressFlower.dismiss();
                        }
                    }
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setCancelable(false)
                            .setTitle("Mobile & E-Mail Verification")
                            //.setMessage("For eSign App will download form it will take some time.")
                            .setMessage("Your Mobile & E-Mail Verification is incomplete, Please complete it to process.")
                            .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                } else {
                    if (AppInfo.IsMobileVerify && AppInfo.IsEmailVerify) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(stateId != null && !stateId.equals("null")) {
                                    if (stateId.equals("4") && AppInfo.IsDigiLockerStatus) {
                                        System.out.println("ReviewFragment : " + "statusid : " + stateId + "digilockerstatus : " + AppInfo.IsDigiLockerStatus);
                                        System.out.println("TESTURLLL" + ">>" + WebServices.getBaseURL +
                                                WebServices.ChangeStatusReview + "?eKycAppId=" + AppInfo.ekycApplicationId + "&StatusId=7" + "&UserId=" + AppInfo.RM_ID + "&clientCode=");
                                        getRes.getResponse(WebServices.getBaseURL +
                                                        WebServices.ChangeStatusReview + "?eKycAppId=" + AppInfo.ekycApplicationId + "&StatusId=7" + "&UserId=" + AppInfo.RM_ID + "&clientCode=",
                                                "ChangeEKycAppStatus",
                                                null);
                                    }else{
                                        System.out.println("ReviewFragment : " + "all are ok : show yes or  no dialoge " + "statusid : " + stateId + "digilockerstatus : " + AppInfo.IsDigiLockerStatus + "esign : " + eSign);
                                        checkPermissionsAndOpenFilePicker_display();

                                    } }
                            }
                        }, 1000);
                    }else {
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                                .setCancelable(false)
                                .setTitle("Mobile & E-Mail Verification")
                                //.setMessage("For eSign App will download form it will take some time.")
                                .setMessage("Your Mobile & E-Mail Verification is incomplete, Please complete it to process.")
                                .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                        builder.show();
                    }} }});

        changeAdditionalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLoanDetails(AppInfo.ekycApplicationId);

                bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        /***
         *open bottom sheet of nominee
         ***/
        nominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddtionalData();
                getLoanDetails(AppInfo.ekycApplicationId);
                bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_EXPANDED);
            }});

        /***
         * Submit Button of nominees
         * Save Nomine data to server
         * ***/

        submit_btn_nomine_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nominee_yes_or_no.equals("")){
                    Toast.makeText(getActivity(),"Please Select Nomination", Toast.LENGTH_LONG).show();

                }else{
                    if(nominee_yes_or_no.equals("Yes")){
                        isAllFieldsChecked = CheckAllFields();
                        if(isAllFieldsChecked) {
                            String percentage=edtpercentagesharing.getText().toString();
                            double isNum = Double.parseDouble(percentage);
                            if(isNum<100.00&&!secondnomineactive&&!isminornominee){
                                Toast.makeText(getActivity(),"Percentage should be 100 % or You have to add one more nominee", Toast.LENGTH_LONG).show();
                            }else if(isNum<100.00 && secondnomineactive){
                                isAllFieldsChecked_of_secondNominee=CheckAllFields_of_nomineesecond();
                                if(isAllFieldsChecked_of_secondNominee){
                                    double nomineoneshare_percent,nomineonetwoshare_percent;
                                    nomineoneshare_percent=Double.parseDouble(edtpercentagesharing.getText().toString());
                                    nomineonetwoshare_percent=Double.parseDouble(edtpercentagesharing2.getText().toString());
                                    double calculate=nomineoneshare_percent+nomineonetwoshare_percent;
                                    if(calculate==100.00){
                                        if(calculate==100.00&&is_Second_minornominee){
                                            secondnominegurdiancheck=CheckAllFields_of_Gurdiansecond();
                                            /*** case:If calculation of percentage is equal to 100 and user*add gurdian****/
                                            if(secondnominegurdiancheck){
                                                Toast.makeText(getActivity(),"Gurdian Detail of second Nominee done", Toast.LENGTH_LONG).show();
                                                nomineedata("Nominee2");
                                                saveNomineedata();
                                            }
                                            //isAllFieldsChecked_of_secondNominee=CheckAllFields_of_nomineesecond();
                                            /***code for add one nomineetwo with  gurdian***/
                                        }else{
                                            /*** code for add one nomineetwo without  gurdian ****/
                                            Toast.makeText(getActivity(),"Add Two Nominee Done", Toast.LENGTH_LONG).show();
                                            nomineedata("Nominee2");
                                            saveNomineedata();
                                        }
                                    }else if(calculate>100){
                                        Toast.makeText(getActivity(),"Sum of percentage sharing should be 100%", Toast.LENGTH_LONG).show();
                                    }else if(calculate==100&&is_Second_minornominee){
                                        Toast.makeText(getActivity(),"Please fill gurdian Detail", Toast.LENGTH_LONG).show();

                                    }else if(calculate<100&&is_Second_minornominee){
                                        /*** case:If calculation of percentage is not =100 and user* add gurdian****/
                                        secondnominegurdiancheck=CheckAllFields_of_Gurdiansecond();
                                        if(secondnominegurdiancheck){
                                            nomineoneshare_percent=Double.parseDouble(edtpercentagesharing.getText().toString());
                                            nomineonetwoshare_percent=Double.parseDouble(edtpercentagesharing2.getText().toString());
                                            double calculatre=nomineoneshare_percent+nomineonetwoshare_percent;
                                            if(calculatre<100&&!thirdnomineactive){
                                                Toast.makeText(getActivity(),"Percentage should be 100 % or You have to add one more nominee 1", Toast.LENGTH_LONG).show();
                                            }else if(calculatre<100&&thirdnomineactive){
                                                //work in progress
                                                isAllFieldsChecked_of_thirdNominee=CheckAllFields_of_nomineethird();
                                                if(isAllFieldsChecked_of_thirdNominee){
                                                    double nomineonethirdshare_percent;
                                                    nomineoneshare_percent=Double.parseDouble(edtpercentagesharing.getText().toString());
                                                    nomineonetwoshare_percent=Double.parseDouble(edtpercentagesharing2.getText().toString());
                                                    nomineonethirdshare_percent=Double.parseDouble(edtpercentagesharing3.getText().toString());
                                                    double calculateofall=nomineoneshare_percent+nomineonetwoshare_percent+nomineonethirdshare_percent;
                                                    if(calculateofall==100&&!is_Third_minornominee){
                                                        /*** code for thhird nominee without gurdian* **/
                                                        //Toast.makeText(getActivity(),"Add successs without gurdian", Toast.LENGTH_LONG).show();
                                                        nomineedata("Nominee3");
                                                        saveNomineedata();
                                                    }else if(calculateofall<100||calculateofall>100){
                                                        Toast.makeText(getActivity(),"Sum of percentage sharing should be 100%", Toast.LENGTH_LONG).show();
                                                    }else if(calculateofall==100&&is_Third_minornominee){
                                                        /*** code for thhird nominee without gurdian* **/
                                                        thirdnominegurdiancheck=CheckAllFields_of_Gurdianthird();
                                                        if(thirdnominegurdiancheck){
                                                            //Toast.makeText(getActivity(),"Gurdian Detail of third Nominee done1", Toast.LENGTH_LONG).show();
                                                            nomineedata("Nominee3");
                                                            saveNomineedata();
                                                        }}}}}}
                                    else{

                                        nomineoneshare_percent=Double.parseDouble(edtpercentagesharing.getText().toString());
                                        nomineonetwoshare_percent=Double.parseDouble(edtpercentagesharing2.getText().toString());
                                        double calculatrerr=nomineoneshare_percent+nomineonetwoshare_percent;
                                        if(calculatrerr<100&&!thirdnomineactive){
                                            Toast.makeText(getActivity(),"Percentage should be 100 % or You have to add one more nominee ", Toast.LENGTH_LONG).show();
                                        }else if(calculatrerr<100&&thirdnomineactive){
                                            //work in progress
                                            isAllFieldsChecked_of_thirdNominee=CheckAllFields_of_nomineethird();
                                            if(isAllFieldsChecked_of_thirdNominee){
                                                double nomineonethirdshare_percent;
                                                nomineoneshare_percent=Double.parseDouble(edtpercentagesharing.getText().toString());
                                                nomineonetwoshare_percent=Double.parseDouble(edtpercentagesharing2.getText().toString());
                                                nomineonethirdshare_percent=Double.parseDouble(edtpercentagesharing3.getText().toString());
                                                double calculateofall=nomineoneshare_percent+nomineonetwoshare_percent+nomineonethirdshare_percent;
                                                if(calculateofall==100&&!is_Third_minornominee){
                                                    /**Add All Nomine Without Gurdian***/
                                                    //Toast.makeText(getActivity(),"All Nominee Add Successfully", Toast.LENGTH_LONG).show();
                                                    nomineedata("Nominee3");
                                                    saveNomineedata();


                                                }else if(calculateofall<100||calculateofall>100){

                                                    Toast.makeText(getActivity(),"Sum of percentage sharing should be 100%", Toast.LENGTH_LONG).show();

                                                }else if(calculateofall==100&&is_Third_minornominee){
                                                    /*** code for thhird nominee without gurdian ***/
                                                    thirdnominegurdiancheck=CheckAllFields_of_Gurdianthird();
                                                    if(thirdnominegurdiancheck){
                                                        //Toast.makeText(getActivity(),"Gurdian Detail of third Nominee done2", Toast.LENGTH_LONG).show();
                                                        nomineedata("Nominee3");
                                                        saveNomineedata();
                                                    }}
                                                else if(calculateofall<100||calculateofall>100&&is_Third_minornominee){
                                                    Toast.makeText(getActivity(),"Sum of percentage sharing should be 100%", Toast.LENGTH_LONG).show();
                                                }}}
                                        // Toast.makeText(getActivity(),"Percentage should be 100 % or You have to add one more nominee 2", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else{
                                if(isminornominee){
                                    isGurdianValidation= GurdianValidations();
                                    if(isGurdianValidation&&isNum<100.00){
                                        Toast.makeText(getActivity(),"Percentage should be 100 % or You have to add one more nominee", Toast.LENGTH_LONG).show();
                                    }else if(isGurdianValidation&&isNum==100){
                                        /**
                                         * code for add one nominee with gurdian
                                         * ***/
                                        //Toast.makeText(getActivity(),"Gurdian Done",Toast.LENGTH_SHORT).show();
                                        nomineedata("Nominee1");
                                        saveNomineedata();

                                    }

                                }else{
                                    /**
                                     * code for add one nominee without gurdian
                                     * ***/
                                    nomineedata("Nominee1");
                                    saveNomineedata();
                                }
                            }
                        }}
                    else {
                        nomineedata("select_no");
                        saveNomineedata();
                    }
                }



            }
        });
        ll_account_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountBottom) {
                    createRotateAnimator(account_plus_img, 0f, 180f).start();
                    account_plus_img.setImageResource(R.drawable.plus);
                    facta_bottom.setVisibility(View.GONE);
                    account_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    AccountBottom = false;

                } else {
                    createRotateAnimator(account_plus_img, 0f, 180f).start();
                    account_plus_img.setImageResource(R.mipmap.ic_remove);
                    facta_plus_img.setImageResource(R.drawable.plus);
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    authorised_plus_img.setImageResource(R.drawable.plus);
                    standing_instruction_plus_img.setImageResource(R.drawable.plus);
                    nomination_plus_img.setImageResource(R.drawable.plus);
                    getLoan_plus_img.setImageResource(R.drawable.plus);

                    account_bottom.setVisibility(View.VISIBLE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    AccountBottom = true;

                }


            }
        });


        facta_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (factaBottomOpen) {

                    createRotateAnimator(facta_plus_img, 0f, 180f).start();
                    facta_plus_img.setImageResource(R.drawable.plus);

                    facta_bottom.setVisibility(View.GONE);
                    account_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    factaBottomOpen = false;

                } else {

                    createRotateAnimator(facta_plus_img, 0f, 180f).start();
                    facta_plus_img.setImageResource(R.mipmap.ic_remove);
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    authorised_plus_img.setImageResource(R.drawable.plus);
                    standing_instruction_plus_img.setImageResource(R.drawable.plus);
                    nomination_plus_img.setImageResource(R.drawable.plus);
                    getLoan_plus_img.setImageResource(R.drawable.plus);

                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.VISIBLE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    factaBottomOpen = true;

                }


            }
        });


        closeAdditonalDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED) {


                    bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_HIDDEN);

                }

            }
        });

        closeAddNominees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehaviorAddNominees.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //Toast.makeText(getActivity(), "click botton", Toast.LENGTH_SHORT).show();
                }
            }
        });

        closeAddNominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehaviorAddNominee.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehaviorAddNominee.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });


        past_action_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pastActionBottomOpen) {
                    createRotateAnimator(past_action_plus_img, 0f, 180f).start();
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    pastActionBottomOpen = false;

                } else {

                    account_plus_img.setImageResource(R.drawable.plus);
                    facta_plus_img.setImageResource(R.drawable.plus);
                    createRotateAnimator(past_action_plus_img, 0f, 180f).start();
                    past_action_plus_img.setImageResource(R.mipmap.ic_remove);
                    authorised_plus_img.setImageResource(R.drawable.plus);
                    nomination_plus_img.setImageResource(R.drawable.plus);
                    getLoan_plus_img.setImageResource(R.drawable.plus);

                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.VISIBLE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    pastActionBottomOpen = true;
                }

            }
        });

        authorised_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (authorisedBottomOpen) {

                    createRotateAnimator(authorised_plus_img, 0f, 180f).start();
                    authorised_plus_img.setImageResource(R.drawable.plus);


                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    authorisedBottomOpen = false;

                }else{

                    account_plus_img.setImageResource(R.drawable.plus);
                    facta_plus_img.setImageResource(R.drawable.plus);
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    createRotateAnimator(authorised_plus_img, 0f, 180f).start();
                    authorised_plus_img.setImageResource(R.mipmap.ic_remove);
                    standing_instruction_plus_img.setImageResource(R.drawable.plus);
                    nomination_plus_img.setImageResource(R.drawable.plus);
                    getLoan_plus_img.setImageResource(R.drawable.plus);
                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.VISIBLE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    authorisedBottomOpen = true;
                }
            }
        });

        standing_instruction_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (standingInsturtionOpen) {
                    createRotateAnimator(standing_instruction_plus_img, 0f, 180f).start();
                    standing_instruction_plus_img.setImageResource(R.drawable.plus);
                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    standingInsturtionOpen = false;

                } else {

                    account_plus_img.setImageResource(R.drawable.plus);
                    facta_plus_img.setImageResource(R.drawable.plus);
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    authorised_plus_img.setImageResource(R.drawable.plus);
                    createRotateAnimator(standing_instruction_plus_img, 0f, 180f).start();
                    standing_instruction_plus_img.setImageResource(R.mipmap.ic_remove);
                    nomination_plus_img.setImageResource(R.drawable.plus);
                    getLoan_plus_img.setImageResource(R.drawable.plus);

                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.VISIBLE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    standingInsturtionOpen = true;
                }


            }
        });

        closeBrockrageBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });

        nomination_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nominationBottom) {
                    createRotateAnimator(nomination_plus_img, 0f, 180f).start();
                    nomination_plus_img.setImageResource(R.drawable.plus);
                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    nominationBottom = false;

                } else {

                    account_plus_img.setImageResource(R.drawable.plus);
                    facta_plus_img.setImageResource(R.drawable.plus);
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    authorised_plus_img.setImageResource(R.drawable.plus);
                    standing_instruction_plus_img.setImageResource(R.drawable.plus);
                    createRotateAnimator(nomination_plus_img, 0f, 180f).start();
                    nomination_plus_img.setImageResource(R.mipmap.ic_remove);
                    getLoan_plus_img.setImageResource(R.drawable.plus);

                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.VISIBLE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    nominationBottom = true;
                }
            }
        });


        getLoan_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLoanBottom) {
                    createRotateAnimator(getLoan_plus_img, 0f, 180f).start();
                    getLoan_plus_img.setImageResource(R.drawable.plus);
                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.GONE);
                    getLoanBottom = false;
                    if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                        submit_btn_additional_detail.setVisibility(View.GONE);
                    } else {
                        submit_btn_additional_detail.setVisibility(View.VISIBLE);
                    }

                } else {

                    account_plus_img.setImageResource(R.drawable.plus);
                    facta_plus_img.setImageResource(R.drawable.plus);
                    past_action_plus_img.setImageResource(R.drawable.plus);
                    authorised_plus_img.setImageResource(R.drawable.plus);
                    standing_instruction_plus_img.setImageResource(R.drawable.plus);
                    createRotateAnimator(getLoan_plus_img, 0f, 180f).start();
                    nomination_plus_img.setImageResource((R.drawable.plus));
                    getLoan_plus_img.setImageResource(R.mipmap.ic_remove);

                    account_bottom.setVisibility(View.GONE);
                    facta_bottom.setVisibility(View.GONE);
                    past_action_bottom.setVisibility(View.GONE);
                    authorisedPerson_bottom.setVisibility(View.GONE);
                    standing_instructiom_bottom.setVisibility(View.GONE);
                    nomination_bottom.setVisibility(View.GONE);
                    getLoan_d_bottom.setVisibility(View.VISIBLE);
                    getLoanBottom = true;
                    if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
                        submit_btn_additional_detail.setVisibility(View.GONE);
                    } else {
                        submit_btn_additional_detail.setVisibility(View.VISIBLE);
                        //basic_detail_save.setEnabled(false);
                        //basic_detail_save.setBackgroundResource(R.drawable.gray_btn);
                    }
                }
            }
        });
        eSignComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

      /*  dobofnominee3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                Calendar calendar = Calendar.getInstance();
                                int currentyear = calendar.get(Calendar.YEAR);

                                int yeardiff=currentyear-year;

                                String birthdateStr = year+"-"+(month+1)+"-"+day;
                                DOB3=year+"-"+(month+1)+"-"+day;
                                int months=month+1;
                                if(months<=9){
                                    birthdateStr = day+"/"+"0"+(month+1)+"/"+year;

                                }else {
                                    birthdateStr = day+"/"+(month+1)+"/"+year;
                                }
                                dobofnominee3.setText(birthdateStr);

                                if(yeardiff<=18){
                                    guardiandetailslayout_thirdNominee.setVisibility(View.VISIBLE);
                                    is_Third_minornominee=true;

                                }else{
                                    is_Third_minornominee=false;
                                    //toggle_button.setVisibility(View.GONE);
                                    guardiandetailslayout_thirdNominee.setVisibility(View.GONE);
                                    //toggleValue = "no";
                                }
                            }
                        },year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });*/

      /*  dobofnominee2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //dobofnominee1.setText(day + "-" + (month + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                int currentyear = calendar.get(Calendar.YEAR);
                                int yeardiff=currentyear-year;
                                String birthdateStr = year+"-"+(month+1)+"-"+day;
                                DOB2=year+"-"+(month+1)+"-"+day;
                                int months=month+1;
                                if(months<=9){
                                    birthdateStr = day+"/"+"0"+(month+1)+"/"+year;
                                }else {
                                    birthdateStr = day+"/"+(month+1)+"/"+year;
                                }
                                dobofnominee2.setText(birthdateStr);
                                if(yeardiff<=18){
                                    guardiandetailslayout_secondNominee.setVisibility(View.VISIBLE);
                                    is_Second_minornominee=true;
                                }else{
                                    is_Second_minornominee=false;
                                    guardiandetailslayout_secondNominee.setVisibility(View.GONE);

                                }
                            }
                        },year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });*/


/*
        dobofnominee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //dobofnominee1.setText(day + "-" + (month + 1) + "-" + year);

                                Calendar calendar = Calendar.getInstance();
                                int currentyear = calendar.get(Calendar.YEAR);
                                int yeardiff=currentyear-year;

                                String birthdateStr = year+"-"+(month+1)+"-"+day;
                                DOB1=year+"-"+(month+1)+"-"+day;
                                int months=month+1;
                                if(months<=9){
                                    birthdateStr = day+"/"+"0"+(month+1)+"/"+year;
                                }else {
                                    birthdateStr = day+"/"+(month+1)+"/"+year;
                                }

                                dobofnominee1.setText(birthdateStr);


                                if(yeardiff<=18){
                                    minorToggle_news.setChecked(true);
                                    //percentagesharingodnomineeone.setText("Percentage(%) Sharing Of Guardian one");
                                    //addressofnomineone.setText("Address of Guardian");
                                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                                    guardiandetailslayout.setVisibility(View.VISIBLE);
                                    toggleValue = "yes";
                                    isminornominee=true;

                                }else{

                                    isminornominee=false;
                                    toggle_button.setVisibility(View.GONE);
                                    guardiandetailslayout.setVisibility(View.GONE);
                                    toggleValue = "no";
                                }
                            }
                        },year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dobofnominee1.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_NEGATIVE) {
                                    dialog.cancel();
                                }
                            }
                        });
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                        "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    DatePicker datePicker = datePickerDialog.getDatePicker();
                                    int month = datePicker.getMonth() + 1;
                                    int checkminoryaer = year - 18;
                                    if (checkminoryaer < datePicker.getYear()) {
                                        String dateaschoosen=datePicker.getDayOfMonth() + "/" + month + "/" + datePicker.getYear();
                                        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy  hh:mm a");
                                        String date = format.format(Date.parse(dateaschoosen));
                                        dobofnominee1.setText(date);
                                        Calendar calendar = Calendar.getInstance();
                                        int year = calendar.get(Calendar.YEAR);
                                    }else {
                                        dobofnominee1.setText("Select Correct date");
                                    }
                                }
                            }
                        });
                  }
               });
*/

        downloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_confirm_file_download);
                LinearLayout menu1 = dialog.findViewById(R.id.menu1);
                LinearLayout menu2 = dialog.findViewById(R.id.menu2);

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        fillEquityFormforPhysical();
                    }});

                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
             }
            });


        physicalUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAadharLinked = "0";
                setAaadharLinkedStatus(getActivity(), isAadharLinked);
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_file_downloaded_or_not);

                LinearLayout menu1 = dialog.findViewById(R.id.menu1);
                LinearLayout menu2 = dialog.findViewById(R.id.menu2);

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        fillEquityFormforPhysical();
                    }
                });

                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (NewEntryFragment.MENU_POSITION > 6) {

                        } else {
                            NewEntryFragment.MENU_POSITION = 6;
                        }
                        System.out.println("MenuPositionAfterDoc" + NewEntryFragment.MENU_POSITION + "");
                        NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                        NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                        NewEntryFragment.step10Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                        TabLayout tabhost = getActivity().findViewById(R.id.tabs);
                        tabhost.getTabAt(6).select();
                    }});
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            }});
        //eSignDigio();
        }
    private void deleta_data(String nomineid) {
        Toast.makeText(getActivity(), "NomineeID=>"+nomineid, Toast.LENGTH_LONG).show();
        String URL_Data="https://stagingekycapi.swastika.co.in/api/AdditionalDetails/DeleteNominee";
        try {
            AndroidNetworking.post(URL_Data)
                    .setPriority(Priority.HIGH)
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    .setTag("test")
                    .addBodyParameter("NomineeId",nomineid)
                    .addBodyParameter("EkycId",eKycId)
                    .addBodyParameter("UpdatedBy","0")
                    .addBodyParameter("KeyFor","Addition")
                    .addBodyParameter("IsNominee","true")
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                System.out.println("deleteresponsedata" + response.toString());
                                String satus = response.getString("status");
                                if(satus.equals("success")) {
                                    Toast.makeText(getActivity(), "delete done", Toast.LENGTH_LONG).show();
                                    callAddtionalData();
                                 }else{
                                    ShowMessage.showErrorMessage(response.getString("message"), getActivity());
                                }
                            }catch(JSONException ex) {
                                System.out.println("Bank_Statement123_ex"+ ">>>>" + ex.getMessage());
                            }}
                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getActivity(), "Something went wrong with your document please check your document whether it is corrupt or not", Toast.LENGTH_LONG).show();
                            //progress.dismiss();
                        }});
        }catch (Exception e) {
            e.printStackTrace();
        }}
    private void deletedata(String nomineid) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Please wait while Loading"); // Setting Title
        progressDialog.show(); //Display Progress Dialog
        progressDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String URL_Data="https://stagingekycapi.swastika.co.in/api/AdditionalDetails/DeleteNominee";

        if(nominee_yes_or_no.equals("Yes")){
            is_nominee="true";
        }else{
            is_nominee="false";
        }
        System.out.println("CAlldataUrl" + URL_Data);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("delete_response" + response.toString());
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String message=jsonObject.getString("message");
                            if(status.equals("success")){
                                JSONObject object=jsonObject.getJSONObject("data");
                                String IsNominee=object.getString("IsNominee");
                                progressDialog.dismiss();
                            }else{
                                Toasty.normal(getActivity(), "error message", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.normal(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }}
        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " +getres_access_token );
                params.put("NomineeId",nomineid );
                params.put("EkycId",AppInfo.ekycApplicationId);
                //params.put("UpdatedBy","0");
                //params.put("KeyFor","Addition" );
                params.put("IsNominee",is_nominee);
                return params;
            }};

        queue.add(stringRequest);
    }

    private void DocumentType() {
        document_typeNominee_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentType_SelectedList(document_typeNominee_1);

            }
        });

        document_typeNominee_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentType_SelectedList(document_typeNominee_2);
            }
        });

        document_typeNominee_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentType_SelectedList(document_typeNominee_3);
            }
        });

        document_typeNomineeGurdian_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentType_SelectedList(document_typeNomineeGurdian_1);
            }
        });

        document_typeNomineeGurdian_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentType_SelectedList(document_typeNomineeGurdian_2);
            }
        });

        document_typeNomineeGurdian_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentType_SelectedList(document_typeNomineeGurdian_3);
            }
        });
    }

    private void DocumentType_SelectedList(TextView document_typeNominee_1) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.dialogview, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        ListView listView = (ListView) popupView.findViewById(R.id.listviewspop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1,selectrelationlistof_document);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.getItem(position);
                document_typeNominee_1.setText(value);
                if (value == "other") {
                    //edtothername.setVisibility(View.VISIBLE);
                }else{
                    //edtothername.setVisibility(View.GONE);
                }
                popupWindow.dismiss();
            }}

        );
        popupWindow.showAsDropDown(document_typeNominee_1, 50, -30);
    }

    private void Relationshipmethod(TextView relationshipapplicant2) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.dialogview, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);
        //searchView.clearFocus();
        // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(relationshipapplicant2.getWindowToken(), 0);
        ListView listView = (ListView) popupView.findViewById(R.id.listviewspop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, selectrelationlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.getItem(position);
                relationshipapplicant2.setText(value);
                if (value == "other") {
                    edtothername.setVisibility(View.VISIBLE);
                }else{
                    edtothername.setVisibility(View.GONE);
                }
                popupWindow.dismiss();
            }}
        );
        popupWindow.showAsDropDown(relationshipapplicant, 50, -30);
    }
    private boolean GurdianValidations() {
        String relationwithapllicant = relationshipapplicant_guardian.getText().toString();
        String city_city = txt_gurdian_city.getText().toString();
        String state_state = txt_gurdian_state.getText().toString();
        String document_type=document_typeNomineeGurdian_1.getText().toString();

        if(edt_gurdiannameone.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Name of Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(relationwithapllicant.isEmpty()) {
            Toast.makeText(getActivity()," Please Select Relation Ship of Gurdian ",Toast.LENGTH_SHORT).show();
            return false;
        }
        String value= edtpercentagesharing.getText().toString();
        if(edt_gurdianaddress_one.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(edt_gurdianaddress_two.length() == 0) {
            //etLastName.setError("This field is required");
            Toast toast = Toast.makeText(getActivity(), "Street Address of Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(edt_gurdianaddress_three.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(state_state.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "State is required 1", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(city_city.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "City is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(editgurdianpincode.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Pincode is required", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
          }
        if(document_type.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Please Select Document type", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
           }
        if(exist_gurdianimage1){
            return true;
        }else{
            if(!nomineeGurdianImage_first){
                Toast.makeText(getActivity(),"Please Upload Identification Document ",Toast.LENGTH_SHORT).show();
                return  false;
            }}
         return true;
        }
    private boolean CheckAllFields_of_Gurdianthird(){
        String relationwithapllicant = relationshipapplicant_guardian3.getText().toString();
        String city_city = txt_gurdian_city3.getText().toString();
        String state_state = txt_gurdian_state3.getText().toString();
        String document_type=document_typeNomineeGurdian_3.getText().toString();
        if(edt_gurdianname3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Name of Third Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(relationwithapllicant.isEmpty()) {
            Toast.makeText(getActivity()," Please Select D.O.B of Third Nominee Gurdian ",Toast.LENGTH_SHORT).show();
            return false;
        }
        String value= edtpercentagesharing.getText().toString();
        if(edt_gurdianaddress_one3.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Third Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(edt_gurdianaddress_two3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Third Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(edt_gurdianaddress_three3.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Third Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (state_state.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "State is required 2", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(city_city.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "City is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(edittextpincode3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Third Nominee Gurdian Pincode is required", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (document_type.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Please Select Document Type", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(exist_gurdianimage3){
            return true;
        }else{
            if(!nomineeGurdianImage_third){
                Toast.makeText(getActivity(),"Please Upload Identification Document ",Toast.LENGTH_SHORT).show();
                return  false;
            }}
        return true;
    }

    private boolean CheckAllFields_of_Gurdiansecond(){
        String relationwithapllicant = relationshipapplicant_guardian2.getText().toString();
        String city_city = txt_gurdian_city2.getText().toString();
        String state_state = txt_gurdian_state2.getText().toString();
        String document_type=document_typeNomineeGurdian_2.getText().toString();

        if(edt_gurdianname2.length() == 0) {
            //etLastName.setError("This field is required");
            Toast toast = Toast.makeText(getActivity(), "Name of Second Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(relationwithapllicant.isEmpty()) {
            Toast.makeText(getActivity(),"Please Select Relationship of Gurdian Second",Toast.LENGTH_SHORT).show();
            return false;
        }
        String value= edtpercentagesharing.getText().toString();
        if(edt_gurdianaddress2.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Second Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(edt_gurdianaddress_two2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Second Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(edt_gurdianaddress_three2.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address of Second Nominee Gurdian is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (state_state.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "State is required 3", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(city_city.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "City is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(edittextpincode2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Second Nominee Gurdian Pincode is required", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (document_type.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Please Select document", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(exist_gurdianimage2){
            return true;
        }else{
            if(!nomineeGurdianImage_second){
                Toast.makeText(getActivity(),"Please Upload Identification Document ",Toast.LENGTH_SHORT).show();
                return  false;
            }}
        return true;
     }

    private boolean CheckAllFields_of_nomineethird(){

        String relationwithapllicant = relationshipapplicant3.getText().toString();
        String city_city =addcity3.getText().toString();
        String state_state =addstate3.getText().toString();
        String document_type=document_typeNominee_3.getText().toString();

        if(edt_nameofnominee3.length() == 0) {
            //edt_firstnominee.setError("This field is required");
            Toast.makeText(getActivity()," Name of Third Nominee  is required ",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtpercentagesharing3.length() == 0) {
            //etLastName.setError("This field is required");
            Toast toast = Toast.makeText(getActivity(), "Percentage is required for Third Nominee ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        String value= edtpercentagesharing3.getText().toString();
        //float finalValue=Float.valueOf(value);

        if(value.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Percentage is required for Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(dobofnominee3.length() == 0) {
            //etLastName.setError("This field is required");
            Toast toast = Toast.makeText(getActivity(), "Please Select D.O.B of Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(relationwithapllicant.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Relaton ship is required of Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (relationwithapllicant.equals("other") && edtothername.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Relaton ship is required of Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (addone3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required for Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if (addtwo3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (addthree3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required for Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (state_state.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "State is required for Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(city_city.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "City is required for Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addpincode3.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Pincode is required for Second Nominee", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
           }
        if(document_type.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Select Document Type for Third Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
           }
        if(exist_nomineimage3){
            return true;
        }else{
            if(!nomineeImage_third){
                Toast.makeText(getActivity(),"Please Upload Identification Document for Third Nominee ",Toast.LENGTH_SHORT).show();
                return  false;
            }}
        return true;
        }

    private boolean CheckAllFields_of_nomineesecond() {
        String relationwithapllicant = relationshipapplicant2.getText().toString();
        String city_city =addcity2.getText().toString();
        String state_state =addstate2.getText().toString();
        String document_type=document_typeNominee_2.getText().toString();

        if(edt_name_of_nominee2.length() == 0) {
            Toast.makeText(getActivity()," Name of Second Nominee  is required ",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtpercentagesharing2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Percentage is required for Second Nominee ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        String value= edtpercentagesharing2.getText().toString();
        if(value.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Percentage is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(dobofnominee2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Please Select D.O.B of Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        if(relationwithapllicant.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Relaton ship is required of Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (relationwithapllicant.equals("other") && edtothername.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Relaton ship is required of Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addres_one_of_nominee2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addres_two_of_nominee2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (addres_three_of_nominee2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (state_state.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "State is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(city_city.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "City is required for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addpincode2.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Pincode is required for Second Nominee", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(document_type.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Select Document Type  for Second Nominee", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
           }
        if(exist_nomineimage2){
            return true;
         }else{
            if(!nomineeImage_second){
                Toast.makeText(getActivity(),"Please Upload Identification Document for Second Nominee ",Toast.LENGTH_SHORT).show();
                return  false;
            }

        }
        return true;
     }


    private boolean CheckAllFields() {
        String relationwithapllicant = relationshipapplicant.getText().toString();
        String city_city = addcity.getText().toString();
        String state_state = addstate.getText().toString();
        String document_type=document_typeNominee_1.getText().toString();

        if(edt_firstnominee.length() == 0) {
            Toast.makeText(getActivity()," Name of Nominee is required ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edtpercentagesharing.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Percentage is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        String value= edtpercentagesharing.getText().toString();
        if(value.length()==0) {
            Toast toast = Toast.makeText(getActivity(), "Percentage is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(dobofnominee1.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Please Select D.O.B", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(relationwithapllicant.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Relaton ship is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (relationwithapllicant.equals("other") && edtothername.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Relaton ship is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addone.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addtwo.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (addthree.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Street Address is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if (state_state.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "State is required 4", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(city_city.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "City is required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(addpincode.length() == 0) {
            Toast toast = Toast.makeText(getActivity(), "Pincode is required", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        if(document_type.isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Please Select Document Type", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
       if(exist_nomineimage1){
         return true;
       }else {
           if(!nomineeImage_first){
               Toast.makeText(getActivity(),"Please Upload Identification Document ",Toast.LENGTH_SHORT).show();
               return  false;
           }}
       return true;
      }




    private void getCityByStateName(String selectedFromList, FragmentActivity activity) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("StateName", selectedFromList);
            AndroidNetworking.post(WebServices.getBaseBankURLString + WebServices.getAllCitiesURL)
                    .addJSONObjectBody(jsonObject) // posting json
                    .setTag("test")
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            Log.e("CityResponse", response.toString());
                            try {
                                JSONArray data = response.getJSONArray("data");
                                Log.e("city data",""+data);
                                cityArray = new String[data.length()];
                                Log.e("ResponseCity>>.", response.toString());
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    cityArray[i] = obj.getString("CityName");
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            } }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Toast.makeText(getActivity(), "Unable to get city from the state", Toast.LENGTH_SHORT).show();
                            if (error.getErrorCode() != 0) {

                                Log.d("CityError", "onError errorCode : " + error.getErrorCode());
                                Log.d("CityError", "onError errorBody : " + error.getErrorBody());
                                Log.d("CityError", "onError errorDetail : " + error.getErrorDetail());
                                // get parsed error object (If ApiError is your class)
                            } else {
                                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                                Log.d("CityError", "onError errorDetail : " + error.getErrorDetail());
                            }
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } }



    public  Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        }else{
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }}}
    private  void test(ProgressDialog progress){
        // Uri picUri = getImageContentUri(getActivity(), Nominee_First);
        Uri picUri = Uri.fromFile(Nominee_First);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),picUri);
            uploadBitmap(bitmap,progress);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void uploadBitmap(final Bitmap bitmap,ProgressDialog progress) {
        String URL_BANK_DOC = WebServices.getBaseURL + WebServices.getUploadNomineeIdentifivationURL;
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_BANK_DOC,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            System.out.println("responsestatusvolley" + response.data);
                            progress.dismiss();
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {

            @Override protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ekycApplicationId",AppInfo.ekycApplicationId);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("Nominee-1",new DataPart(imagename + ".jpeg",getFileDataFromDrawable(bitmap)));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +getres_access_token);
                System.out.println("UploadImage : " + "access_token : " +AppInfo.access_token);
                System.out.println("UploadImage : " + "getres_access_token : " +getres_access_token);
                System.out.println("UploadImage : " + "Header : " +headers);
                return headers;
            }

        };


        try {
            requestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSwastikaSSLSocketFactory()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestQueue.add(volleyMultipartRequest).setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        //adding the request to volley
        //  Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void Send_Nominee_Images(ProgressDialog progress) {
        BitmapDrawable drawable = (BitmapDrawable) testImageIV.getDrawable();
        Bitmap bitmapConverted = drawable.getBitmap();
        try{
            no_imagefile = new File(getActivity().getCacheDir(), "temp.jpg");
            no_imagefile.createNewFile();
            Bitmap bitmap = bitmapConverted;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(no_imagefile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        }catch (Exception ex){
            System.out.println("NO_FILE_IMAGE is null exception=>"+ex.getMessage());
        }
        System.out.println("DemoImage" + no_imagefile);
        if(Nominee_First==null){
            Nominee_First=no_imagefile;
        }
        if(Nominee_Second==null){
            Nominee_Second=no_imagefile;
        }
        if(Nominee_Third==null){
            Nominee_Third=no_imagefile;
        }
        if(Guardian_First==null){
            Guardian_First=no_imagefile;
        }
        if(Guardian_Second==null){
            Guardian_Second=no_imagefile;
        }
        if(Guardian_Third==null){
            Guardian_Third=no_imagefile;
        }

        String URL_BANK_DOC = WebServices.getBaseURL + WebServices.getUploadNomineeIdentifivationURL;
        System.out.println("ekycApplicationId" +AppInfo.ekycApplicationId);
        System.out.println("Nominee-1" + Nominee_First);
        System.out.println("Nominee-2" + Nominee_Second);
        System.out.println("Nominee-3" + Nominee_Third);
        System.out.println("Gurdian-1" + Guardian_First);
        System.out.println("Gurdian-2" + Guardian_Second);
        System.out.println("Gurdian-3" + Guardian_Third);

        try{
            AndroidNetworking.upload(URL_BANK_DOC)
                    .setPriority(Priority.HIGH)
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    .setTag("test")
                    //.addMultipartParameter("DocumentType", "Income")
                    .addMultipartParameter("ekycApplicationId",AppInfo.ekycApplicationId)
                    .addMultipartParameter("KeyFor","Addition")
                    .addMultipartParameter("RequestId","0")
                    .addMultipartFile("Nominee-1", Nominee_First)
                    .addMultipartFile("Guardian-1",Guardian_First)
                    .addMultipartFile("Nominee-2", Nominee_Second)
                    .addMultipartFile("Guardian-2",Guardian_Second)
                    .addMultipartFile("Nominee-3", Nominee_Third)
                    .addMultipartFile("Guardian-3",Guardian_Third)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                System.out.println("NomineImages_123>>>>" + response.toString());
                                String satus = response.getString("status");
                                if(satus.equals("success")) {
                                    progress.dismiss();
                                }else{
                                    ShowMessage.showErrorMessage(response.getString("message"), getActivity());
                                    progress.dismiss();
                                }
                            }catch (JSONException ex) {
                                System.out.println("Bank_Statement123_ex"+ ">>>>" + ex.getMessage());
                            }}

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getActivity(), "Something went wrong with your document please check your document whether it is corrupt or not", Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }});
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("localhost", session);
            }
        };
    }

    private void filter_delivery(String text) {
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        //creating a new array list to filter our data.
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();
        if(text.isEmpty()) {
            filteredlist.addAll(Cash_Delivery_ArrayList);
        } else {
            // running a for loop to compare elements.
            for (BrokerageModel item : Cash_Delivery_ArrayList) {
                //for(int i = 0; i < Cash_Intraday_ArrayList.size(); i++){
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                // checking if the entered string matched with any item of our recycler view.

                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getDESCRIPTION_INFO().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
        }

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            stringArrayAdapter_brokerage_Delivery.filterList(filteredlist);
        }

        stringArrayAdapter_brokerage_Delivery.notifyDataSetChanged();
    }


    private void filter_DerivativeFuture(String text) {
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();
        if (text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        } else {
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getONESIDEMIN().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                } } }
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if(filteredlist.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        }else{
            stringArrayAdapter_DerivativeFuture.filterList(filteredlist);
        }
        stringArrayAdapter_DerivativeFuture.notifyDataSetChanged();
    }


    private void filter_DerivativeOption(String text) {
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();
        if (text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        } else {
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getONESIDEMIN().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                } } }
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {

            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            stringArrayAdapter_DerivativeOption.filterList(filteredlist);
        }
        stringArrayAdapter_DerivativeOption.notifyDataSetChanged();
    }
//stringArrayAdapter_currencyDerivativeFuture

    private void filter_currencyDerivativeFuture(String text){

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        // creating a new array list to filter our data.
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();

        if (text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        } else {
            // running a for loop to compare elements.
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                //for(int i = 0; i < Cash_Intraday_ArrayList.size(); i++){
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                // checking if the entered string matched with any item of our recycler view.

                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getONESIDEPER().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
        }

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        }else{
            stringArrayAdapter_currencyDerivativeFuture.filterList(filteredlist);
        }
        stringArrayAdapter_currencyDerivativeFuture.notifyDataSetChanged();
    }



    private void filter_commDerivativeFuture(String text){

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        // creating a new array list to filter our data.
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();

        if (text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        } else {
            // running a for loop to compare elements.
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                //for(int i = 0; i < Cash_Intraday_ArrayList.size(); i++){
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                // checking if the entered string matched with any item of our recycler view.

                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getONESIDEMIN().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }}}
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        }else{
            stringArrayAdapter_commoditiyDerivativeOption.filterList(filteredlist);
        }
        stringArrayAdapter_commoditiyDerivativeOption.notifyDataSetChanged();
    }
    private void filter_commDerivativeOption(String text){
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        //creating a new array list to filter our data.
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();
        if(text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        }else {
            // running a for loop to compare elements.
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                //for(int i = 0; i < Cash_Intraday_ArrayList.size(); i++){
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                // checking if the entered string matched with any item of our recycler view.

                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getONESIDEPER().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
        }
        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        }else{
            stringArrayAdapter_commoditiyDerivativeFuture.filterList(filteredlist);
        }
        stringArrayAdapter_commoditiyDerivativeFuture.notifyDataSetChanged();
    }




    private void filter_currencyDerivativeOption(String text){

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        // creating a new array list to filter our data.
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();

        if (text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        } else {
            // running a for loop to compare elements.
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                //for(int i = 0; i < Cash_Intraday_ArrayList.size(); i++){
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                // checking if the entered string matched with any item of our recycler view.

                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getONESIDEMIN().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
        }

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {

            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();

        }else{
            stringArrayAdapter_currencyDerivativeOption.filterList(filteredlist);
        }

        stringArrayAdapter_currencyDerivativeOption.notifyDataSetChanged();
    }





    private void filter_Intraday(String text) {

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  text : " + text);
        // creating a new array list to filter our data.
        ArrayList<BrokerageModel> filteredlist = new ArrayList<BrokerageModel>();

        if (text.isEmpty()) {
            filteredlist.addAll(Cash_Intraday_ArrayList);
        } else {
            // running a for loop to compare elements.
            for (BrokerageModel item : Cash_Intraday_ArrayList) {
                //for(int i = 0; i < Cash_Intraday_ArrayList.size(); i++){
                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  item : " + item);
                // checking if the entered string matched with any item of our recycler view.

                System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  (item.getDESCRIPTION_INFO(). : " + item);
                if (item.getDESCRIPTION_INFO().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
        }

        System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filter :  filteredlist. : " + filteredlist);
        if (filteredlist.isEmpty()) {

            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            stringArrayAdapter_brokerage_Intraday.filterList(filteredlist);
        }

        stringArrayAdapter_brokerage_Intraday.notifyDataSetChanged();
    }


    public class RecyclerCustomAdapter_Delivery extends RecyclerView.Adapter<RecyclerCustomAdapter_Delivery.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;

        public RecyclerCustomAdapter_Delivery(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.brokerage_adapter, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {
                if(dataList.get(position).getDESCRIPTION_INFO() != null) {
                    System.out.println("GetBrokerageDetail : stock_delivery : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getDESCRIPTION_INFO());
                }

                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_delivery : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));
                        System.out.println("GetBrokerageDetail : stock_delivery : " + "selectedFromList : " + selectedFromList);
                        Cash_Delivery_MODULE_NO = dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : stock_delivery : " + "Cash_Delivery_MODULE_NO : " + Cash_Delivery_MODULE_NO);
                        Cash_Delivery_DELIVERYPER = dataList.get(position).getONESIDEPER();
                        System.out.println("GetBrokerageDetail : stock_delivery : " + "Cash_Delivery_DELIVERYPER : " + Cash_Delivery_DELIVERYPER);
                        Cash_Delivery_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : stock_delivery : " + "Cash_Delivery_COMPANY_CODE : " + Cash_Delivery_COMPANY_CODE);
                        stock_delivery.setText(Cash_Delivery_DELIVERYPER);
                        brokerage_dialog.dismiss();
                    }});
            }catch (Exception e) {
            }
        }

        // method for filtering our recyclerview items.
        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {

            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);
                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }

    public class RecyclerCustomAdapter_Intraday extends RecyclerView.Adapter<RecyclerCustomAdapter_Intraday.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;

        public RecyclerCustomAdapter_Intraday(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.brokerage_adapter, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {

                if (dataList.get(position).getDESCRIPTION_INFO() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getDESCRIPTION_INFO());
                }

                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");

                        String selectedFromList = String.valueOf(dataList.get(position));
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "selectedFromList : " + selectedFromList);

                        Cash_intraday_MODULE_NO = dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "Cash_intraday_MODULE_NO : " + Cash_intraday_MODULE_NO);

                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEPER();
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "Cash_intraday_ONESIDEPER : " + Cash_intraday_ONESIDEPER);

                        Cash_intraday_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "Cash_intraday_COMPANY_CODE : " + Cash_intraday_COMPANY_CODE);
                        stock_intraday.setText(Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();
                    }
                });


            } catch (Exception e) {
            }
        }


        public void filterList(ArrayList<BrokerageModel> filterllist) {

            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {
            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);
                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }


    //RecyclerCustomAdapter_CurrencyDerivativeFuture stringArrayAdapter_currencyDerivativeFuture;
    public class RecyclerCustomAdapter_CurrencyDerivativeFuture extends RecyclerView.Adapter<RecyclerCustomAdapter_CurrencyDerivativeFuture.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;


        public RecyclerCustomAdapter_CurrencyDerivativeFuture(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.derivativeoption_adapterlayout, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {
                if(dataList.get(position).getONESIDEMIN() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getONESIDEPER());
                }
                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));

                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEPER();
                        currency_derivatives_futures_MODULE_NO = dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "currency_derivatives_futures_MODULE_NO : " + currency_derivatives_futures_MODULE_NO);

                        currency_derivatives_futures_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : currency_derivatives_futures : " + "currency_derivatives_futures_COMPANY_CODE : " + currency_derivatives_futures_COMPANY_CODE);

                        System.out.println("GetBrokerageDetailbrokerage_data" + currency_derivatives_futures_MODULE_NO + "==DD== " + Cash_intraday_ONESIDEPER);
                        currency_derivatives_futures.setText(Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();
                    }
                });
            } catch (Exception e) {

            }
        }

        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {

            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);

                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }




    //RecyclerCustomAdapter_CommoditiyDerivativeOption stringArrayAdapter_commoditiyDerivativeOption;

    public class RecyclerCustomAdapter_CommoditiyDerivativeOption extends RecyclerView.Adapter<RecyclerCustomAdapter_CommoditiyDerivativeOption.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;
        public RecyclerCustomAdapter_CommoditiyDerivativeOption(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.derivativeoption_adapterlayout, parent, false);
            return new MyViewHolderForGrid(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {

                if (dataList.get(position).getONESIDEMIN() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getONESIDEMIN());
                }

                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));
                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEMIN();
                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "selectedFromList : " + selectedFromList);
                        commodity_derivatives_options_MODULE_NO =dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "commodity_derivatives_options_MODULE_NO : " + commodity_derivatives_options_MODULE_NO);
                        commodity_derivatives_options_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : commodity_derivatives_options : " + "commodity_derivatives_options_COMPANY_CODE : " + commodity_derivatives_options_COMPANY_CODE);
                        commodity_derivatives_options.setText(Cash_intraday_ONESIDEPER);
                        System.out.println("GetBrokerageDetailbrokerage_data" + commodity_derivatives_options_MODULE_NO + "==DD== " + Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();
                    }
                });
            } catch (Exception e) {

            }
        }

        //method for filtering our recyclerview items.
        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {

            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);

                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }}}
    //RecyclerCustomAdapter_CommoditiyDerivativeFuture stringArrayAdapter_commoditiyDerivativeFuture;
    public class RecyclerCustomAdapter_CommoditiyDerivativeFuture extends RecyclerView.Adapter<RecyclerCustomAdapter_CommoditiyDerivativeFuture.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;


        public RecyclerCustomAdapter_CommoditiyDerivativeFuture(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.derivativeoption_adapterlayout, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {
                if(dataList.get(position).getONESIDEMIN() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getONESIDEPER());
                }
                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));
                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEPER();
                        currency_derivatives_options.setText(Cash_intraday_ONESIDEPER);
                        commodity_derivatives_futures_MODULE_NO = dataList.get(position).getMODULE_NO();
                        commodity_derivatives_futures_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        commodity_derivatives_futures.setText(Cash_intraday_ONESIDEPER);
                        System.out.println("GetBrokerageDetailbrokerage_data" + commodity_derivatives_futures_MODULE_NO + "==DD== " + Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();
                    }
                });
            } catch (Exception e) {
            }}

        //method for filtering our recyclerview items.
        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {
            TextView brokerage_data;
            LinearLayout brokerage_ada;
            public MyViewHolderForGrid(View itemView) {
                super(itemView);

                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }









    //RecyclerCustomAdapter_CurrencyDerivativeOption stringArrayAdapter_currencyDerivativeOption;
    public class RecyclerCustomAdapter_CurrencyDerivativeOption extends RecyclerView.Adapter<RecyclerCustomAdapter_CurrencyDerivativeOption.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;


        public RecyclerCustomAdapter_CurrencyDerivativeOption(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.derivativeoption_adapterlayout, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {

                if (dataList.get(position).getONESIDEMIN() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getONESIDEMIN());
                }

                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));

                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "selectedFromList : " + selectedFromList);


                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEMIN();
                        currency_derivatives_options_MODULE_NO = dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "currency_derivatives_options_MODULE_NO : " + currency_derivatives_options_MODULE_NO);
                        currency_derivatives_options_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : currency_derivatives_options : " + "currency_derivatives_options_COMPANY_CODE : " + currency_derivatives_options_COMPANY_CODE);

                        currency_derivatives_options.setText(Cash_intraday_ONESIDEPER);
                        System.out.println("GetBrokerageDetailbrokerage_data" + currency_derivatives_options_MODULE_NO + "==DD== " + Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();





                    }
                });
            } catch (Exception e) {

            }
        }

        //method for filtering our recyclerview items.
        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {

            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);

                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }

    //RecyclerCustomAdapter_DerivativeFuture
    public class RecyclerCustomAdapter_DerivativeFuture extends RecyclerView.Adapter<RecyclerCustomAdapter_DerivativeFuture.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;


        public RecyclerCustomAdapter_DerivativeFuture(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.derivativeoption_adapterlayout, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {

                if (dataList.get(position).getONESIDEMIN() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getONESIDEMIN());
                }

                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));
                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEMIN();
                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "selectedFromList : " + selectedFromList);
                        derivative_future_MODULE_NO = dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "derivative_future_MODULE_NO : " + derivative_future_MODULE_NO);
                        derivative_future_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : derivatives_futures : " + "derivative_future_COMPANY_CODE : " + derivative_future_COMPANY_CODE);
                        derivatives_futures.setText(Cash_intraday_ONESIDEPER);
                        System.out.println("GetBrokerageDetailbrokerage_data" + derivative_future_MODULE_NO + "==DD== " + Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();
                    }
                });
            } catch (Exception e) {

            }
        }

        //method for filtering our recyclerview items.
        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {

            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);

                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }


    //RecyclerCustomAdapter_DerivativeOption

    public class RecyclerCustomAdapter_DerivativeOption extends RecyclerView.Adapter<RecyclerCustomAdapter_DerivativeOption.MyViewHolderForGrid> {
        private Context context;
        ArrayList<BrokerageModel> dataList;

        public RecyclerCustomAdapter_DerivativeOption(Context context, ArrayList<BrokerageModel> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolderForGrid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.derivativeoption_adapterlayout, parent, false);
            return new MyViewHolderForGrid(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderForGrid holder, @SuppressLint("RecyclerView") int position) {
            try {
                if(dataList.get(position).getONESIDEMIN() != null) {
                    System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data set  : ");
                    holder.brokerage_data.setText(dataList.get(position).getONESIDEMIN());
                }
                holder.brokerage_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "brokerage_data on click : ");
                        String selectedFromList = String.valueOf(dataList.get(position));
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "selectedFromList : " + selectedFromList);
                        System.out.println("GetBrokerageDetail : derivatives_options : " + "selectedFromList : " + selectedFromList);
                        derivatives_options_MODULE_NO = dataList.get(position).getMODULE_NO();
                        System.out.println("GetBrokerageDetail : derivatives_options : " + "derivatives_options_MODULE_NO : " + derivatives_options_MODULE_NO);
                        derivatives_options_COMPANY_CODE = dataList.get(position).getCOMPANY_CODE();
                        System.out.println("GetBrokerageDetail : derivatives_options : " + "derivatives_options_COMPANY_CODE : " + derivatives_options_COMPANY_CODE);
                        Cash_intraday_ONESIDEPER = dataList.get(position).getONESIDEMIN();
                        System.out.println("GetBrokerageDetail : stock_intraday : " + "Cash_intraday_ONESIDEPER : " + Cash_intraday_ONESIDEPER);
                        derivatives_options.setText(Cash_intraday_ONESIDEPER);
                        brokerage_dialog.dismiss();

                        System.out.println("GetBrokerageDetailbrokerage_data" + derivatives_options_MODULE_NO + "==DD== " + Cash_intraday_ONESIDEPER);

                    }
                });
            } catch (Exception e) {

            }
        }

        //method for filtering our recyclerview items.
        public void filterList(ArrayList<BrokerageModel> filterllist) {
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  filterList : " + filterllist);
            dataList = filterllist;
            System.out.println("GetBrokerageDetail : stock_delivery : " + " in  dataList : " + dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void remove(int position) {
            if (position > 0 && position < dataList.size()) {
                dataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        public class MyViewHolderForGrid extends RecyclerView.ViewHolder {

            TextView brokerage_data;
            LinearLayout brokerage_ada;

            public MyViewHolderForGrid(View itemView) {
                super(itemView);

                brokerage_data = itemView.findViewById(R.id.brokerage_data);
                brokerage_ada = itemView.findViewById(R.id.brokerage_ada);
            }
        }
    }

    //durgesh
    private void pickupImageNew(String titleDoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_pop_up_layout_with_document, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        TextView txtheader = dialogView.findViewById(R.id.txtheader);
        txtheader.setText("Upload " + titleDoc);

        TextView txtcapture = dialogView.findViewById(R.id.txtcapture);
        txtcapture.setText("Capture " + titleDoc);

        TextView txtDocument = dialogView.findViewById(R.id.txtDocument);
        //txtDocument.setText("Upload PDF " + titleDoc);
        txtDocument.setText("Upload PDF from files");
        TextView txtgallary = dialogView.findViewById(R.id.txtgallary);
        //txtgallary.setText("Upload " + titleDoc);
        txtgallary.setText("Upload picture from gallery");
        txtDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                DocumentType = "PDF";
                checkPermissionsAndOpenFilePicker();
            }
        });

        txtcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                openCameraIntent();
            }});


        txtgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                browseDocuments("Hello");
            }
        });

        alertDialog.show();

    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        String permission2 = Manifest.permission.MANAGE_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 1000);

            }
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker() {
        new MaterialFilePicker()
                .withSupportFragment(this)
                .withRequestCode(3000)
                .withHiddenFiles(false)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Tap to Open")
                .start();
    }
    private void showError() {
        Toast.makeText(getActivity(), "Please enable 'Files and Media' permission from the Settings under eKYC App Swastika to continue.", Toast.LENGTH_SHORT).show();
    }

    private void pickupImage_of_Nominee(String titleDoc){
        //String titleDoc = "Brokerage";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_pop_up_layout_with_document, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        TextView txtheader = dialogView.findViewById(R.id.txtheader);
        txtheader.setText("Upload Image");
        TextView txtcapture = dialogView.findViewById(R.id.txtcapture);
        txtcapture.setText("Capture Image");
        TextView txtDocument = dialogView.findViewById(R.id.txtDocument);
        txtDocument.setText("Upload Document" + titleDoc);
        txtDocument.setVisibility(View.GONE);
        TextView txtgallary = dialogView.findViewById(R.id.txtgallary);
        txtgallary.setText("Upload Image From Gallery");
        txtcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                openCameraIntent();
            }});
        txtgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                browseDocuments("Hello");
            }});
        alertDialog.show();
    }


    private void pickupImage() {
        String titleDoc = "Brokerage";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_pop_up_layout_with_document, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        TextView txtheader = dialogView.findViewById(R.id.txtheader);
        txtheader.setText("Upload " + titleDoc);
        TextView txtcapture = dialogView.findViewById(R.id.txtcapture);
        txtcapture.setText("Capture " + titleDoc);
        TextView txtDocument = dialogView.findViewById(R.id.txtDocument);
        txtDocument.setText("Upload Document" + titleDoc);
        txtDocument.setVisibility(View.GONE);
        TextView txtgallary = dialogView.findViewById(R.id.txtgallary);
        txtgallary.setText("Upload " + titleDoc);
        txtcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                openCameraIntent();
            }});

        txtgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                browseDocuments("Hello");
            }});
        alertDialog.show();
    }

    private void browseDocuments(String hello) {
        {
            String[] mimeTypes = {"image/*"};
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            }else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent, "Select Document"), 4567);
        }}

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        }catch (Exception ex) {
            System.out.println("photoFile_IOException" + "" + ex.getMessage());

        }
        if (photoFile != null) {
            System.out.println("PhotoFile" + "NotNull");
            photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
            if (photoURI != null) {
                System.out.println("Photo" + ">>>" + photoURI);
            }else{
                System.out.println("Photo" + "Null");
            }
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(pictureIntent, 7890);
        }else {
            System.out.println("PhotoFile" + "Null");
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }catch (IOException e) {
            e.printStackTrace();
        }
        imageFilePath = image.getAbsolutePath();
        return image;
    }


    private void nomineedata( String nominetype){
        AdditionalObj = new JSONObject();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        try {
            AdditionalObj.put("AcOpenLocation", "NSDL");
            AdditionalObj.put("AdditionalDetailsId","0");
            AdditionalObj.put("FATCA_CountryOfCitizenship", "Other");
            AdditionalObj.put("FATCA_IsUsPerson","Yes");
            AdditionalObj.put("FATCA_TaxResidence", "Other");
            AdditionalObj.put("PAST_ActionsTaken","Yes");
            AdditionalObj.put("PAST_ActionsTakenValue", "");
            AdditionalObj.put("SB_ClientCode","");
            AdditionalObj.put("SB_DetailsOfDisputes", "");
            AdditionalObj.put("SB_IsAnotherAuthorisedDealing", "No");
            AdditionalObj.put("SB_IsMemberOrSubBrokerOrAPOfAnyExchange", "");
            AdditionalObj.put("SB_NameOfAuthorisedPersonSubBroker", "");
            AdditionalObj.put("SB_NameOfExchange", "");
            AdditionalObj.put("SB_NameOfStockBroker", "");
            AdditionalObj.put("SB_SEBIRegistrationNumber", "");
            AdditionalObj.put("SI_ContractNoteHoldingTransactionStatement", "Electronically");
            AdditionalObj.put("SI_DeclareEmailAddress", "Self");
            AdditionalObj.put("SI_DeclareMobileNumber", "Self");
            AdditionalObj.put("SI_ReceiveAnnualReport", "Electronically");
            AdditionalObj.put("SI_ReceiveDPAccountsStatement", "As per SEBI regulation");
            AdditionalObj.put("SI_ReceiveDeliveryInstructionSlip", "No");
            AdditionalObj.put("SI_ShareEmailIdWithRegistrar", "Yes");
            AdditionalObj.put("SI_TRUSTSMSAlertFacility", "Yes");
            AdditionalObj.put("ekycApplicationId",AppInfo.ekycApplicationId);
            //AdditionalObj.put("Medium", "Mobile");
            AdditionalObj.put("IsNominee",nominee_yes_or_no);
            AdditionalObj.put("Medium","Mobile");
            AdditionalObj.put("CreatedBy", AppInfo.RM_ID);
            AdditionalObj.put("CreadedDate", formattedDate);


            if(!nominee_yes_or_no.equals("No")) {
                JSONObject Nomine1 = new JSONObject();
                try {
                    Nomine1.put("ekycApplicationId", AppInfo.ekycApplicationId);

                    if(nomineeId1.equals("")){
                        Nomine1.put("NomineeId", "1");
                    }else{
                        Nomine1.put("NomineeId", nomineeId1);
                    }

                    Nomine1.put("NomineeName",edt_firstnominee.getText().toString());
                    Nomine1.put("NomineeDocumentType",document_typeNominee_1.getText().toString());
                    if(first_Nominee_Address){
                        Nomine1.put("AddressSameAsAccountHolder", "Yes");
                        Nomine1.put("NomineeAddress1","");
                        Nomine1.put("NomineeAddress2","");
                        Nomine1.put("NomineeAddress3","");
                        Nomine1.put("NomineeCity", "");
                        Nomine1.put("NomineeState","");
                        Nomine1.put("NomineePinCode","");
                    }else{
                        Nomine1.put("AddressSameAsAccountHolder", "No");
                        Nomine1.put("NomineeAddress1", addone.getText().toString());
                        Nomine1.put("NomineeAddress2", addtwo.getText().toString());
                        Nomine1.put("NomineeAddress3", addthree.getText().toString());
                        Nomine1.put("NomineeCity", addcity.getText().toString());
                        Nomine1.put("NomineeState",addstate.getText().toString());
                        Nomine1.put("NomineePinCode", addpincode.getText().toString());
                    }

                    Nomine1.put("PanCard", "");
                    Nomine1.put("NomineePhone", "");
                    Nomine1.put("RelationshipWithNominee",relationshipapplicant.getText().toString());
                    Nomine1.put("DOB",DOB1);
                    Nomine1.put("IsMinor",isminornominee);
                    Nomine1.put("GuardiansName",edt_gurdiannameone.getText().toString());
                    Nomine1.put("GuardianDocumentType",document_typeNomineeGurdian_1.getText().toString());

                    if(gurdiansaddressselcted){
                        Nomine1.put("GuardianAddressSameAsAccountHolder", "Yes");
                        Nomine1.put("GuardiansAddress1","");
                        Nomine1.put("GuardiansAddress2","");
                        Nomine1.put("GuardiansAddress3","");
                        Nomine1.put("GuardianCity","");
                        Nomine1.put("GuardianState","");
                        Nomine1.put("GuardianPinCode","");
                    }else{
                        Nomine1.put("GuardianAddressSameAsAccountHolder", "No");
                        Nomine1.put("GuardiansAddress1",edt_gurdianaddress_one.getText().toString());
                        Nomine1.put("GuardiansAddress2",edt_gurdianaddress_two.getText().toString());
                        Nomine1.put("GuardiansAddress3",edt_gurdianaddress_three.getText().toString());
                        Nomine1.put("GuardianCity",txt_gurdian_city.getText().toString());
                        Nomine1.put("GuardianState",txt_gurdian_state.getText().toString());
                        Nomine1.put("GuardianPinCode",editgurdianpincode.getText().toString());

                    }

                    Nomine1.put("GuardiansPhone", "");
                    Nomine1.put("RelationshipWithGuardian", relationshipapplicant_guardian.getText().toString());
                    Nomine1.put("GuardiansDate", "2020-08-03");
                    Nomine1.put("GuardiansPlace", "");
                    Nomine1.put("SubmittedPlace", "");
                    Nomine1.put("Sequence", "1");
                    Nomine1.put("SharePercentage", edtpercentagesharing.getText().toString());

                }catch (JSONException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
                JSONObject Nomine2 = new JSONObject();
                try {
                    Nomine2.put("ekycApplicationId", AppInfo.ekycApplicationId);

                    if(nomineeId2.equals("")){
                        Nomine2.put("NomineeId", "2");
                    }else{
                        Nomine2.put("NomineeId",nomineeId2);
                    }

                    Nomine2.put("NomineeName", edt_name_of_nominee2.getText().toString());
                    Nomine2.put("NomineeDocumentType", document_typeNominee_2.getText().toString());
                    if(secondn_Nominee_Address){
                        Nomine2.put("AddressSameAsAccountHolder", "Yes");
                        Nomine2.put("NomineeAddress1","");
                        Nomine2.put("NomineeAddress2","");
                        Nomine2.put("NomineeAddress3","");
                        Nomine2.put("NomineeCity","");
                        Nomine2.put("NomineePinCode","");
                        Nomine2.put("NomineeState","");

                    }else{
                        Nomine2.put("AddressSameAsAccountHolder", "No");
                        Nomine2.put("NomineeAddress1",addres_one_of_nominee2.getText().toString());
                        Nomine2.put("NomineeAddress2", addres_two_of_nominee2.getText().toString());
                        Nomine2.put("NomineeAddress3", addres_three_of_nominee2.getText().toString());
                        Nomine2.put("NomineeCity", addcity2.getText().toString());
                        Nomine2.put("NomineePinCode", addpincode2.getText().toString());
                        Nomine2.put("NomineeState", addstate2.getText().toString());
                    }

                    Nomine2.put("NomineeAddress1",addres_one_of_nominee2.getText().toString());
                    Nomine2.put("NomineeAddress2", addres_two_of_nominee2.getText().toString());
                    Nomine2.put("NomineeAddress3", addres_three_of_nominee2.getText().toString());
                    Nomine2.put("NomineeCity", addcity2.getText().toString());
                    Nomine2.put("NomineePinCode", addpincode2.getText().toString());
                    Nomine2.put("PanCard", "");
                    Nomine2.put("NomineePhone", "");
                    Nomine2.put("RelationshipWithNominee", relationshipapplicant2.getText().toString());
                    Nomine2.put("DOB", DOB2);
                    Nomine2.put("IsMinor", is_Second_minornominee);
                    Nomine2.put("GuardiansName",edt_gurdianname2.getText().toString());
                    Nomine2.put("GuardianDocumentType", document_typeNomineeGurdian_2.getText().toString());
                    if(gurdiansaddressselcted_ofsecondnominee){
                        Nomine2.put("GuardianAddressSameAsAccountHolder", "Yes");
                        Nomine2.put("GuardiansAddress1","");
                        Nomine2.put("GuardiansAddress2","");
                        Nomine2.put("GuardiansAddress3","");
                        Nomine2.put("GuardianCity","");
                        Nomine2.put("GuardianState","");
                        Nomine2.put("GuardianPinCode","");

                    }else{
                        Nomine2.put("GuardianAddressSameAsAccountHolder", "No");
                        Nomine2.put("GuardiansAddress1",edt_gurdianaddress2.getText().toString());
                        Nomine2.put("GuardiansAddress2",edt_gurdianaddress_two2.getText().toString());
                        Nomine2.put("GuardiansAddress3",edt_gurdianaddress_three2.getText().toString());
                        Nomine2.put("GuardianCity", txt_gurdian_city2.getText().toString());
                        Nomine2.put("GuardianState",txt_gurdian_state2.getText().toString());
                        Nomine2.put("GuardianPinCode",edittextpincode2.getText().toString());
                    }
                    Nomine2.put("GuardiansPhone", "");
                    Nomine2.put("RelationshipWithGuardian",relationshipapplicant_guardian2.getText().toString());
                    Nomine2.put("GuardiansDate", "2020-08-03");
                    Nomine2.put("GuardiansPlace", "");
                    Nomine2.put("SubmittedPlace", "");
                    Nomine2.put("Sequence", "2");
                    Nomine2.put("SharePercentage",edtpercentagesharing2.getText().toString());
                }catch(JSONException e) {
                    e.printStackTrace();
                }

                JSONObject Nomine3 = new JSONObject();
                try{
                    Nomine3.put("ekycApplicationId", AppInfo.ekycApplicationId);

                    if(nomineeId3.equals("")){
                        Nomine3.put("NomineeId", "3");
                    }else{
                        Nomine3.put("NomineeId", nomineeId3);
                    }
                    Nomine3.put("NomineeName", edt_nameofnominee3.getText().toString());
                    Nomine3.put("NomineeDocumentType",document_typeNominee_3.getText().toString());
                    if(third_Nominee_Address){
                        Nomine3.put("AddressSameAsAccountHolder", "Yes");
                        Nomine3.put("NomineeAddress1","");
                        Nomine3.put("NomineeAddress2","");
                        Nomine3.put("NomineeAddress3","");
                        Nomine3.put("NomineeCity","");
                        Nomine3.put("NomineeState","");
                        Nomine3.put("NomineePinCode","");

                    }else{
                        Nomine3.put("AddressSameAsAccountHolder", "No");
                        Nomine3.put("NomineeAddress1",addone3.getText().toString());
                        Nomine3.put("NomineeAddress2",addtwo3.getText().toString());
                        Nomine3.put("NomineeAddress3",addthree3.getText().toString());
                        Nomine3.put("NomineeCity", addcity3.getText().toString());
                        Nomine3.put("NomineeState",addstate3.getText().toString());
                        Nomine3.put("NomineePinCode", addpincode3.getText().toString());
                    }

                    Nomine3.put("PanCard", "");
                    Nomine3.put("NomineePhone", "");
                    Nomine3.put("RelationshipWithNominee", relationshipapplicant3.getText().toString());
                    Nomine3.put("DOB", DOB3);
                    Nomine3.put("IsMinor", is_Third_minornominee);
                    Nomine3.put("GuardiansName", edt_gurdianname3.getText().toString());
                    Nomine3.put("GuardianDocumentType", document_typeNomineeGurdian_3.getText().toString());

                    if(gurdiansaddressselcted_ofthirdnominee){
                        Nomine3.put("GuardianAddressSameAsAccountHolder", "Yes");
                        Nomine3.put("GuardiansAddress1","");
                        Nomine3.put("GuardiansAddress2","");
                        Nomine3.put("GuardiansAddress3","");
                        Nomine3.put("GuardianCity","");
                        Nomine3.put("GuardianState","");
                        Nomine3.put("GuardianPinCode","");

                    }else{
                        Nomine3.put("GuardianAddressSameAsAccountHolder", "No");
                        Nomine3.put("GuardiansAddress1", edt_gurdianaddress_one3.getText().toString());
                        Nomine3.put("GuardiansAddress2", edt_gurdianaddress_two3.getText().toString());
                        Nomine3.put("GuardiansAddress3", edt_gurdianaddress_three3.getText().toString());
                        Nomine3.put("GuardianCity", txt_gurdian_city3.getText().toString());
                        Nomine3.put("GuardianState",txt_gurdian_state3.getText().toString());
                        Nomine3.put("GuardianPinCode",edittextpincode3.getText().toString());
                    }

                    Nomine3.put("GuardiansPhone", "");
                    Nomine3.put("RelationshipWithGuardian", relationshipapplicant_guardian3.getText().toString());
                    Nomine3.put("GuardiansDate", "2020-08-03");
                    Nomine3.put("GuardiansPlace", "");
                    Nomine3.put("SubmittedPlace", "");
                    Nomine3.put("Sequence", "1");
                    Nomine3.put("SharePercentage", edtpercentagesharing3.getText().toString());
                }catch(JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jsonArray = new JSONArray();
                if(nominetype.equals("Nominee1")){
                    jsonArray.put(Nomine1);
                    AdditionalObj.put("NomineeDetails", jsonArray);
                }else if(nominetype.equals("Nominee2")){
                    jsonArray.put(Nomine1);
                    jsonArray.put(Nomine2);
                    AdditionalObj.put("NomineeDetails", jsonArray);
                }else if(nominetype.equals("Nominee3")){
                    jsonArray.put(Nomine1);
                    jsonArray.put(Nomine2);
                    jsonArray.put(Nomine3);
                    AdditionalObj.put("NomineeDetails", jsonArray);
                }
            }else{
            }
            System.out.println("AdditionalObj" + AdditionalObj.toString());
        }catch(JSONException e) {
            e.printStackTrace();
        }
        if(bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if(bottomSheetBehaviorAddNominee.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorAddNominee.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if(bottomSheetBehaviorAddNominees.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
    private void saveNomineedata()
    {
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        System.out.println("uploadAddtionalData" + AdditionalObj.toString());
        String url = WebServices.getBaseURL + WebServices.uploadNomineData;
        System.out.println("save_data_urlofNominee" + url);
        try {
            AndroidNetworking.post(url)
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    .addJSONObjectBody(AdditionalObj) // posting json
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("uploadAddtionalData123" + response.toString());
                            try {
                                String status=response.getString("status");
                                String message=response.getString("message");
                                if(status.equals("success")){
                                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                    if(!nominee_yes_or_no.equals("No")){
                                        if(nomineeImage_first||nomineeImage_second||nomineeImage_third||nomineeGurdianImage_first
                                                ||nomineeGurdianImage_second||nomineeImage_third){
                                            Send_Nominee_Images(progress);
                                        }else {
                                            progress.dismiss();
                                        }
                                    }else{
                                        progress.dismiss();
                                    }
                                }else{
                                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                            }catch(JSONException e) {
                                e.printStackTrace();
                            }  }
                        @Override
                        public void onError(ANError error) {
                            if (error.getErrorCode() != 0) {
                                System.out.println("uploadAddtionalData123" + error.getErrorCode());
                                System.out.println("uploadAddtionalData123" + error.getErrorBody());
                                System.out.println("uploadAddtionalData123" + error.getErrorDetail());
                            }else{
                                System.out.println("uploadAddtionalData123" +  error.getErrorDetail());
                            }
                        }});
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void callAdditionaldata() {
        System.out.println("uploadAddtionalData" + AdditionalObj.toString());
        String url = WebServices.getBaseURL + WebServices.uploadAddtionalData;
        try {
            AndroidNetworking.post(url)
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    .addJSONObjectBody(AdditionalObj) //posting json
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("uploadAddtionalData123" + response.toString());
                        }
                        @Override
                        public void onError(ANError error) {
                            //handle error
                        }
                    });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void Callsubmitdata() {
        if(bottomSheetBehaviorBrockrage.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            if (isLessThanRequired()) {
                if (documentUploaded) {
                    if (turnOverEdit.getText().toString().length() == 0
                            || Integer.parseInt(turnOverEdit.getText().toString()) == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Values that you have entered are less than our plan for this you have to provide use the  additional detail at the bottom of the page.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        dialog.dismiss();
                                        brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                        brokerageScroll.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                            }
                                        }); }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        setBrokerage();
                        bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Please upload old contract note to save brokerage.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                    brokerageScroll.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            brokerageScroll.fullScroll(View.FOCUS_DOWN);
                                        }
                                    });
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                System.out.println("OPEN_ELSe>>" + "bottomSheetBehaviorBrockrage");
                setBrokerage();
                bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }else if(bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            System.out.println("OPEN>>" + "bottomSheetBehaviorAdditionDoc");
        }else if (bottomSheetBehaviorAddNominee.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            System.out.println("OPEN>>" + "bottomSheetBehaviorAddNominee");
        }else if (bottomSheetBehaviorAddNominees.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            System.out.println("OPEN>>" + "bottomSheetBehaviorAddNominees");
        }else {
            String currentStatu = currentstatus;

            System.out.println("NewAddressScreen:" + "currentStatu" + currentStatu);

            if (currentStatu.equals("Rejected")) {
                for (int i = 0; i < AppInfo.getStateMaster.size(); i++) {

                    if (AppInfo.getStateMaster.get(i).getStatusName().equals("Review")) {

                        if (AppInfo.ekycFormType.equals("Manual")) {
                            NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                            NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                            NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                            NewEntryFragment.step10Line.setBackgroundColor(Color.parseColor("#19e9b5"));
                            TabLayout tabhost = getActivity().findViewById(R.id.tabs);
                            tabhost.getTabAt(6).select();

                        } else {
                            final String status = AppInfo.getStateMaster.get(i).getStatusId();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Are you sure you want to submit?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                                    .themeColor(WHITE)
                                                    .fadeColor(DKGRAY).build();
                                            acProgressFlower.show();
                                            System.out.println("TESTURLLL" + ">>" + WebServices.getBaseURL +
                                                    WebServices.ChangeStatusReview + "?eKycAppId=" + AppInfo.ekycApplicationId + "&StatusId=" + status + "&UserId=" + AppInfo.RM_ID + "&clientCode=");
                                            getRes.getResponse(WebServices.getBaseURL +
                                                            WebServices.ChangeStatusReview + "?eKycAppId=" + AppInfo.ekycApplicationId + "&StatusId=" + status + "&UserId=" + AppInfo.RM_ID + "&clientCode=",
                                                    "ChangeEKycAppStatus",
                                                    null);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
            } else {
                userDocuments userDocs = FinalApplicationForm.getUserDocuments();
                System.out.println("geteSigned" + ">>" + userDocs.geteSigned());
                if (!userDocs.geteSigned()) {

                    openAaadharQuestionDialog(getActivity());

                } else {
                } } } }


    public void GeteSignSourceDetails() {
        try {
            HashMap<String, Object> parameters;
            getResponse getRes = new getResponse(getActivity());
            parameters = new HashMap<String, Object>();

            if (BuildConfig.FLAVOR.equalsIgnoreCase("Swastika")) {
                parameters.put("CompanyId", "1");
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase("Tradingo")) {
                parameters.put("CompanyId", "2");
            }

            getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.GeteSignSourceDetails,
                    "GeteSignSourceDetails",
                    parameters, "POST");

            System.out.println("GeteSignSourceDetails" + "parameters" + parameters);

        } catch (Exception ex) {

            System.out.println("GeteSignSourceDetails" + "ExceptionOnCall" + ex.getMessage());
        }
    }

    public void openNewAaadharQuestionDialog(Context con) {
        final Dialog dialog = new Dialog(con);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.new_aadhaar_confirmation_popup);

        Button menu1 = dialog.findViewById(R.id.btnNo);
        Button menu2 = dialog.findViewById(R.id.btnYes);
        TextView btnDontKnow = dialog.findViewById(R.id.btnDontKnow);

        btnDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                String ADHAARSITE_URL = "https://resident.uidai.gov.in/verify-email-mobile";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(ADHAARSITE_URL));
                startActivity(intent);
            }
        });

        // No Button
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AppInfo.IsAadharLink = false;
                Log.d("IsKraValue", AppInfo.IsKRA + "");
                if (AppInfo.IsKRA) {
                    if (AppInfo.IsEmailVerify) {

                        if (stateId.equals("4") && eSign) {

                            Callsubmitdata();
                        } else {
                            if (eSign) {
                                Intent intent = new Intent(getActivity(), SaveClientCode.class);
                                startActivity(intent);
                            } else {
                                // Esign On Emudra screen
                                NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                NewEntryFragment.step10Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                                tabhost.getTabAt(8).select();
                            }
                        }

                    } else {
                        // Email Verify Screen
                        NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                        tabhost.getTabAt(6).select();
                    }
                } else {
                    // Address screen
                    Log.d("IsKraValue", AppInfo.IsKRA + "ELSE");
                    NewEntryFragment.step4Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                    NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                    NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                    NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                    NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                    NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                    tabhost.getTabAt(9).select();
                }
            }
        });

        // YES Button
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AppInfo.IsAadharLink = true;
                if (AppInfo.IsKRA) {
                    if (AppInfo.IsEmailVerify) {
                        // eSign On With NSDL
                        newProcessToEsignYesOpt();

                    } else {
                        // Email Verify Screen
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                        tabhost.getTabAt(6).select();
                    }
                } else {
                    if (AppInfo.IsEmailVerify) {
                        // Digi Loker Screen
                        isAadharLinked = "1";
                        setAaadharLinkedStatus(getActivity(), isAadharLinked);
                        NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                        tabhost.getTabAt(7).select();
                    } else {
                        // Email Verify Screen
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step5Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step6Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step7Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step8Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        NewEntryFragment.step9Line.setBackgroundColor(Color.parseColor("#e6e7ee"));
                        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                        tabhost.getTabAt(6).select();
                    }
                } }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    public void openAaadharQuestionDialog(Context con) {
        final Dialog dialog = new Dialog(con);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_confirm_aadhar);

        LinearLayout menu1 = dialog.findViewById(R.id.menu1);
        LinearLayout menu2 = dialog.findViewById(R.id.menu2);
        final LinearLayout esignMudra = dialog.findViewById(R.id.menu3);
        //LinearLayout menu3 = dialog.findViewById(R.id.menu3);
        TextView menu3 = dialog.findViewById(R.id.tvDonKnow);

        esignMudra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


                /********************  E-MUDRA ANDROID INEGRATION BY API SUNEEL SIR ********************
                 //                Intent i = new Intent(getActivity(), EmudraActivity.class);
                 startActivity(i);*/

                /****************  E-MUDRA ANDROID INEGRATION **************************/

                Log.d("eMudhraAccountStatus", eMudhraAccountStatus + "--");
                if (eMudhraAccountStatus.equals("Inactive")) {
                    getEmudraAccountStatus();

                } else {
                    if (acProgressFlower != null) {
                        acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(WHITE)
                                .fadeColor(DKGRAY).build();
                        acProgressFlower.show();
                    }
                    fillEquityFormForEmudra();
                }
            }
        });

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isAadharLinked = "1";
                setAaadharLinkedStatus(getActivity(), isAadharLinked);
                if (isCommoditySelected) {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setCancelable(false)
                            .setTitle("eSign Form")
                            //.setMessage("For eSign App will download form it will take some time.")
                            .setMessage("for esign, The form will be download on your phone. It may take some time.")
                            .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    fillEquityForm();
                                }
                            });
                    builder.show();


                } else {

                    // Fill only equity form

                    // Create Alert using Builder
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setCancelable(false)
                            .setTitle("Equity Form")
//                            .setMessage("For eSign App will download equity form it will take some time.")
                            .setMessage("for esign, The form will be download on your phone. It may take some time.")
                            .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //checkeSignStatus();
                                    fillEquityForm();
                                }
                            });
                    builder.show();

                }

            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                boolean check = false;
                if (check) {
                    startActivity(new Intent(getActivity(), AccountOpeningFormActivity.class));
                } else {
                    dialog.dismiss();
                    // Aadhar is not linked with mobile
                    isAadharLinkedOrNot = false;
//                setAaadharLinkedStatus(getActivity(), isAadharLinked);
                    IsBoolean = true;
                    review_applicationMainLayout.setVisibility(View.GONE);
                    error_with_aadhar.setVisibility(View.GONE);
                    congratulationsLayout.setVisibility(View.GONE);
                    aadhar_not_linked.setVisibility(View.VISIBLE);
                    //additional_doc.setVisibility(View.GONE);
                    reviewWebView.setVisibility(View.GONE);
                }
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAaadharLinkedStatus(getActivity(), 0);
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    public void onResume() {
        super.onResume();

        if (digioDownloaded) {

            System.out.println("DocumentIDDigio" + digioDocumentId);
            getSignedDocument();

            Toast.makeText(getActivity(),
                    digioDocumentId + " Document Downloaded",
                    Toast.LENGTH_LONG
            ).show();

        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("return.response");
        getActivity().registerReceiver(ReceivefromService, filter);
        //the first parameter is the name of the inner class we created.
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getActivity().unregisterReceiver(ReceivefromService);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Receiver not registered")) {
                Log.i("TAG", "Tried to unregister the receiver when it's not registered");
            } else {
                throw e;
            }
        }
    }

    public void setAaadharLinkedStatus(Context con, String value) {

        try {
            HashMap<String, Object> parameters;
            getResponse getRes = new getResponse(con);

            parameters = new HashMap<String, Object>();
            parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
            parameters.put("isAadharLinked", value);
            getRes.getResponseFromURL(WebServices.getBaseURL +
                            WebServices.isAadharLinkdOrNotSaveStatus,
                    "setAaadharLinkedStatus",
                    parameters, "POST");

        } catch (Exception ex) {

            System.out.println("ExceptionOnCall" + ex.getMessage());
        }

    }


    public void download_forPhysicalDoc(String Equity_url) {

        new DownloadFile_forPhysicalDoc().execute(Equity_url, filledFormFileName);
    }


    public void download(String equity_url) {
        new DownloadFile().execute(equity_url, filledFormFileName);
//        String URL = Service.SALARY_PDF + "userid=" + us.get(0).getMainid() + "&month=" + selectedmonth + "&year=" + selectYear.getText().toString();
        System.out.println("SALARY_URL" + equity_url);

        new DownloadTask(getActivity(), equity_url);
    }

    public void downloadForEmudra(String equity_url) {
        new DownloadFileForEmudra().execute(equity_url, filledFormFileName);
//        String URL = Service.SALARY_PDF + "userid=" + us.get(0).getMainid() + "&month=" + selectedmonth + "&year=" + selectYear.getText().toString();
        System.out.println("SALARY_URL" + equity_url);

        // new DownloadTask(getActivity(), equity_url);
    }


    private void checkPermissionsAndOpenFilePicker_display() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        String permission2 = Manifest.permission.MANAGE_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 1000);
            }
        }
      /*  else if (ContextCompat.checkSelfPermission(getActivity(), permission2) != PackageManager.PERMISSION_GRANTED)
        {
            if (SDK_INT >= Build.VERSION_CODES.R)
            {
                if (!Environment.isExternalStorageManager()) {
                    System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted if ");
                    dialog_for_allow_all_access_permission_display();
                }
                else{
                    System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted else 1");

                    GeteSignSourceDetails();
                }
            }
            else
            {
                GeteSignSourceDetails();
            }
        }*/
        else {
            GeteSignSourceDetails();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }

            case 2296: {

                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        openFilePicker();
                        // perform action when allow permission success
                    } else {
                        Toast.makeText(getActivity(), "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            case 2295: {

                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        GeteSignSourceDetails();
                        // perform action when allow permission success
                    } else {
                        Toast.makeText(getActivity(), "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }

    public void dialog_for_allow_all_access_permission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_for_allow_access_all_permission, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button allow_access = (Button) dialogView.findViewById(R.id.allow_access);

        allow_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                check_permission();
            }
        });

        alertDialog.show();

    }

    public void dialog_for_allow_all_access_permission_display() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_for_allow_access_all_permission, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button allow_access = (Button) dialogView.findViewById(R.id.allow_access);

        allow_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                check_permission_display();
            }
        });

        alertDialog.show();

    }

    public void check_permission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted if ");
                try {
                    System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted try ");

                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getActivity().getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                } catch (Exception e) {
                    System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted catch :   " + e.getMessage());

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2296);
                }
            } else {
                System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted else 1");

                openFilePicker();
            }
        } else {
            System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted else 2");

            openFilePicker();
        }
    }

    public void check_permission_display() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted if ");
                try {
                    System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted try ");

                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getActivity().getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2295);
                } catch (Exception e) {
                    System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted catch :   " + e.getMessage());

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2295);
                }
            } else {
                System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted else 1");

                GeteSignSourceDetails();
            }
        } else {
            System.out.println("User_updated_file_upload : " + "MANAGE_EXTERNAL_STORAGE  :  not granted else 2");

            GeteSignSourceDetails();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (data != null) {
        switch (requestCode) {

            case 100: {
                String eSignResponse = data.getStringExtra("signedResponse");
            }
            break;
            case 4567:
                if (data != null) {
                    if (data.getData() != null) {
                        Uri phuri = data.getData();

                        beginCrop(phuri);
                    }

                }
                break;

            case 7890: {

                if (checkValidUri(photoURI)) {

                    beginCrop(photoURI);
                }

            }

            break;

            case Crop.REQUEST_CROP:

                if(data != null) {
                    photoURI = Crop.getOutput(data);
                    aadharCardFileBack = new File(photoURI.getPath());


                    if (data != null) {

                    /*Toast.makeText(getActivity(),
                            "CropResult>>>"+ titleDoc,
                            Toast.LENGTH_LONG).show();*/

                        photoURI = Crop.getOutput(data);

                        if (photoURI != null) {

                            if (titleDoc.equals("Cheque One")) {
                                // Selected Document is Aadhar Card
                                chequeFileOne = new File(photoURI.getPath());

                                if (getFileSizeinKb(photoURI) > 700000) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                } else {

                                    try {
                                        isChequeOneUploaded = true;
                                        getLoanDetailsButtonvalidation();
                                        cheque1fBox1Image.setImageBitmap(decodeUri(photoURI));
                                        cheque1fBox1Image.setImageBitmap(decodeUri(photoURI));
//                            btbtn.setBackgroundColor(Color.parseColor("#00000000"));
//                            submit_btn.setBackgroundResource(R.drawable.gray_btn);
//                            submit_btn.setEnabled(false);
                                        tempHoldImageView.setImageBitmap(decodeUriTemp(photoURI));
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("FileSize" + getFileSizeinKb(photoURI) + "");
                                    uploadDocuments("Cheque One", "");
                                }

                            } else if (titleDoc.equals("Cheque Two")) {
                                // Selected Document is Aadhar Card
                                chequeFileTwo = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();


                                } else {


                                    //    isIncomeTaxSalarySlipCancellCheque = true;


                                    try {

                                        isChequeTwoUploaded = true;
                                        getLoanDetailsButtonvalidation();
                                        uploadCheque2Box2ImageTwo.setImageBitmap(decodeUri(photoURI));
                                        uploadCheque2Box2ImageTwo.setImageBitmap(decodeUri(photoURI));
                                        uploadCheque2Box2ImageTwo.setImageBitmap(decodeUriTemp(photoURI));
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }


                                    uploadDocuments("Cheque Two", "");
                                }


                            } else if (titleDoc.equals("Bank Statement")) {
                                // Selected Document is Aadhar Card


                                bankStatementFile = new File(photoURI.getPath());


                                if (getFileSizeinKb(photoURI) > 700000) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();


                                }else{

                                    try{
                                        isbankStatementUploaded = true;
                                        getLoanDetailsButtonvalidation();
                                        BankBox1Image.setImageBitmap(decodeUri(photoURI));
                                        BankBox1Image.setImageBitmap(decodeUri(photoURI));
                                        BankBox1Image.setImageBitmap(decodeUriTemp(photoURI));
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }


                                    uploadDocuments("Bank Statement", "");
                                }


                            } else if (titleDoc.equals("Brokerage")) {
                                try {
                                    brokageImg.setImageBitmap(decodeUri(photoURI));
                                    tempHoldImageView.setImageBitmap(decodeUriTemp(photoURI));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                brokageImg.setVisibility(View.VISIBLE);
                                brokTxt.setText("CHANGE CONTRACT NOTE");
                                documentUploaded = true;
                                System.out.println("FileSize" + getFileSizeinKb(photoURI) + "");

                                if(getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                } else {
                                    callEditableForAditionalOne();
                                }
                                // Toast.makeText(getActivity(), "Unable to get image please try again", Toast.LENGTH_LONG).show();


                            }else if(titleDoc.equals("Nominee_First")){

                                Nominee_First = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }});
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    try {

                                        //nomineeImage_first=false,nomineeImage_second=false,nomineeImage_third=false;
                                        nomineeImage_first=true;
                                        exist_nomineimage1=false;
                                        value=1;
                                        nominee_oneImageview.setVisibility(View.VISIBLE);
                                        nominee_oneImageview.setImageBitmap(decodeUri(photoURI));
                                        nominee_oneImageview.setImageBitmap(decodeUri(photoURI));
                                        //userIncomeProofImageBox.setImageBitmap(decodeUriTempBankStatement(photoURI));

                                    }catch(FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    //isIncomeproofFileSelected = true;
                                    //isIncomeTaxSalarySlipCancellCheque = true;
                                    System.out.println("FileSize"+ getFileSizeinKb(photoURI) + "");
                                    //uploadDocuments("IncomeProof", "");
                                }}

                            //GurdianNominee_First_Image
                            else if(titleDoc.equals("GurdianNominee_First_Image")){
                                Guardian_First = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }});
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    try {
                                        nomineeGurdianImage_first=true;
                                        exist_gurdianimage1=false;
                                        gurdian_imageview.setVisibility(View.VISIBLE);
                                        gurdian_imageview.setImageBitmap(decodeUri(photoURI));
                                        gurdian_imageview.setImageBitmap(decodeUri(photoURI));
                                    }catch(FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("FileSize"+ getFileSizeinKb(photoURI) + "");
                                }
                            }
                            else if(titleDoc.equals("GurdianNominee_Third_Image")){
                                Guardian_Third = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }});
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    try{
                                        nomineeGurdianImage_third=true;
                                        exist_gurdianimage3=false;
                                        gurdian_imageviewthird.setVisibility(View.VISIBLE);
                                        gurdian_imageviewthird.setImageBitmap(decodeUri(photoURI));
                                        gurdian_imageviewthird.setImageBitmap(decodeUri(photoURI));
                                    }catch(FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("FileSize"+ getFileSizeinKb(photoURI) + "");
                                     }
                               }
                            else if(titleDoc.equals("GurdianNominee_Second_Image")){
                                Guardian_Second = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }});
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    try{
                                        nomineeGurdianImage_second=true;
                                        exist_gurdianimage2=false;
                                        gurdian_Second_imageview.setVisibility(View.VISIBLE);
                                        gurdian_Second_imageview.setImageBitmap(decodeUri(photoURI));
                                        gurdian_Second_imageview.setImageBitmap(decodeUri(photoURI));

                                    }catch(FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("FileSize"+ getFileSizeinKb(photoURI) + "");
                                }}
                            //Nominee_Second,Nominee_Third
                            else if(titleDoc.equals("Nominee_Second")){
                                Nominee_Second = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }});
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    try {
                                        nomineeImage_second=true;
                                        exist_nomineimage2=false;
                                        second_nominee_image.setVisibility(View.VISIBLE);
                                        second_nominee_image.setImageBitmap(decodeUri(photoURI));
                                        second_nominee_image.setImageBitmap(decodeUri(photoURI));
                                        //userIncomeProofImageBox.setImageBitmap(decodeUriTempBankStatement(photoURI));

                                    }catch(FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    //isIncomeproofFileSelected = true;
                                    //isIncomeTaxSalarySlipCancellCheque = true;
                                    System.out.println("FileSize"+ getFileSizeinKb(photoURI) + "");
                                    //uploadDocuments("IncomeProof", "");
                                }}
                            else if(titleDoc.equals("Nominee_Third")){
                                Nominee_Third = new File(photoURI.getPath());
                                if (getFileSizeinKb(photoURI) > 700000) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Your file size is greater than required! Please upload another file.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                }});
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }else{
                                    try {

                                        nomineeImage_third=true;
                                        exist_nomineimage3=false;
                                        Third_nominee_image.setVisibility(View.VISIBLE);
                                        Third_nominee_image.setImageBitmap(decodeUri(photoURI));
                                        Third_nominee_image.setImageBitmap(decodeUri(photoURI));

                                    }catch(FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("FileSize"+ getFileSizeinKb(photoURI) + "");

                                }}



                        }
                    }
                }

                break;

            case 3000:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String selectedFilePath = "";
                    selectedFilePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

                    if (data != null) {

                        long fileSizeInKB = 0, fileSizeInMB = 0;
                        try {

                            Uri filePath = Uri.parse(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
//                        calldata(data);

                            System.out.println("URIL" + filePath.toString() + "");
                            System.out.println("FilePath" + selectedFilePath + "");
                            if (selectedFilePath != null) {
                                PDFfile = new File(selectedFilePath);
                            }
                            try {
                                isPasswordProtected = false;
                                PdfReader pdfReader = new PdfReader(String.valueOf(PDFfile));
                                pdfReader.isEncrypted();
                                long fileSizeInBytes = PDFfile.length();
                                fileSizeInKB = fileSizeInBytes / 1024;
                                fileSizeInMB = fileSizeInKB / 1024;

                            } catch (IOException e) {
                                e.printStackTrace();
                                if (e.getMessage().toString().equals("Bad user password")) {
                                    isPasswordProtected = true;
                                } else {
                                    isPasswordProtected = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("FileException" + e.getMessage());
                        }

                        if (fileSizeInMB > 2) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Your file size is greater than required! (2MB) Please upload another file.")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();

                        } else {

                            if (titleDoc.equals("Cheque One")) {

                                System.out.println("FilePath" + selectedFilePath + "");
                                if (selectedFilePath != null) {
                                    PDFfile = new File(selectedFilePath);
                                    chequeFileOnePdf = new File(selectedFilePath);
                                }

                                if (isPasswordProtected) {
                                    openPasswordProtectedDiloagAdditioanlDocOne();
                                } else {

                                    isChequeOneUploaded = true;
                                    getLoanDetailsButtonvalidation();
                                    cheque1fBox1Image.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                    cheque1fBox1Image.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));

                                    uploadDocuments("Cheque OnePdf", "");

                                }

                            } else if (titleDoc.equals("Cheque Two")) {
                                chequeFileTwoPdf = new File(selectedFilePath);
                                if (isPasswordProtected) {
                                } else {
                                    isChequeTwoUploaded = true;
                                    getLoanDetailsButtonvalidation();
                                    uploadCheque2Box2ImageTwo.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                    uploadCheque2Box2ImageTwo.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));

                                    uploadDocuments("Cheque TwoPdf", "");
                                }
                            } else if (titleDoc.equals("Bank Statement")) {
                                System.out.println("titleDoc" + titleDoc);
                                bankStatementPdf = new File(selectedFilePath);

                                if (isPasswordProtected) {
                                    openPasswordProtectedDiloagForBankStatement();
                                } else {
                                    isbankStatementUploaded = true;
                                    getLoanDetailsButtonvalidation();
                                    BankBox1Image.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                    BankBox1Image.setImageDrawable(getResources().getDrawable(R.mipmap.new_pdf_image));
                                    uploadDocuments("BankStatementPdf", "");
                                }
                            }else{
                                Toast.makeText(getActivity(), "PDF is null", Toast.LENGTH_SHORT).show();
                            }}}}}
                        }

    private void openPasswordProtectedDiloagAdditioanlDocTwo() {
        pdfAlertDialog = new AlertDialog.Builder(getActivity());
        pdfAlertDialog.setTitle("Alert");
        pdfAlertDialog.setMessage("This PDF Have Password protection so please remove it.");
        pdfAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        pdfAlertDialog.setCancelable(false);
        pdfAlertDialog.show();
    }

    private void openPasswordProtectedDiloagForBankStatement() {
        pdfAlertDialog = new AlertDialog.Builder(getActivity());
        pdfAlertDialog.setTitle("Alert");
        pdfAlertDialog.setMessage("This PDF Have Password protection so please remove it.");
        pdfAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        pdfAlertDialog.setCancelable(false);
        pdfAlertDialog.show();
    }

    private void openPasswordProtectedDiloagAdditioanlDocOne() {
        pdfAlertDialog = new AlertDialog.Builder(getActivity());
        pdfAlertDialog.setTitle("Alert");
        pdfAlertDialog.setMessage("This PDF Have Password protection so please remove it.");
        pdfAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                selectedFilePathForPdf = "";

                dialogInterface.dismiss();
            }
        });
        pdfAlertDialog.setCancelable(false);
        pdfAlertDialog.show();
    }

    public void uploadDocuments(String type, String parameters) {
        getResponse getRes;

        String reqType = "POST";

        if (NewEntryFragment.MENU_POSITION > 5) {
            reqType = "POST";
        } else {
            reqType = "POST";
        }


        switch (type) {
            case "Cheque One":

                Log.d("URLDocUpload", WebServices.getBaseURL + WebServices.getLoanUploadDocument + "");
                Log.d("ekycId", AppInfo.ekycApplicationId + "");
                Log.d("documentType", "GetLoan_Cheque1" + "");
                Log.d("file", chequeFileOne + "");


                getLoanDetailsButtonvalidation();
                getRes = new getResponse(getActivity());

                // aadhar_doc_image1
                getRes.UploadImageGetLoan(cheque1fBox1Image, "" +
                                "" + WebServices.getBaseURL + WebServices.getLoanUploadDocument,
                        "GetLoan_Cheque1",
                        AppInfo.ekycApplicationId,
                        reqType);

                break;

            case "Cheque OnePdf":
                Log.d("URLDocUpload", WebServices.getBaseURL + WebServices.getLoanUploadDocument + "");
                Log.d("ekycId", AppInfo.ekycApplicationId + "");
                Log.d("documentType", "GetLoan_Cheque1" + "");
                Log.d("file", chequeFileOne + "");

                getLoanDetailsButtonvalidation();

                getRes = new getResponse(getActivity());

                // aadhar_doc_image1
                getRes.UploadPDFGetLoanDocument(chequeFileOnePdf, "" +
                                "" + WebServices.getBaseURL + WebServices.getLoanUploadDocument,
                        "GetLoan_Cheque1",
                        AppInfo.ekycApplicationId,
                        reqType);

                break;

            case "Cheque Two":

                getLoanDetailsButtonvalidation();
                Log.d("URLDocUpload", WebServices.getBaseURL + WebServices.getLoanUploadDocument + "");
                Log.d("ekycId", AppInfo.ekycApplicationId + "");
                Log.d("documentType", "GetLoan_Cheque2" + "");
                Log.d("file", chequeFileTwo + "");
                getRes = new getResponse(getActivity());

                // aadhar_doc_image1
                getRes.UploadImageGetLoan(uploadCheque2Box2ImageTwo, "" +
                                "" + WebServices.getBaseURL + WebServices.getLoanUploadDocument,
                        "GetLoan_Cheque2",
                        AppInfo.ekycApplicationId,
                        reqType);

                break;


            case "Cheque TwoPdf":
                getLoanDetailsButtonvalidation();

                Log.d("URLDocUpload", WebServices.getBaseURL + WebServices.getLoanUploadDocument + "");
                Log.d("ekycId", AppInfo.ekycApplicationId + "");
                Log.d("documentType", "GetLoan_Cheque2" + "");
                Log.d("file", chequeFileTwo + "");
                getRes = new getResponse(getActivity());

                // aadhar_doc_image1
                getRes.UploadPDFGetLoanDocument(chequeFileTwoPdf, "" +
                                "" + WebServices.getBaseURL + WebServices.getLoanUploadDocument,
                        "GetLoan_Cheque2",
                        AppInfo.ekycApplicationId,
                        reqType);

                break;

            case "Bank Statement":
                getLoanDetailsButtonvalidation();

                Log.d("URLDocUpload", WebServices.getBaseURL + WebServices.getLoanUploadDocument + "");
                Log.d("ekycId", AppInfo.ekycApplicationId + "");
                Log.d("documentType", "GetLoan_Cheque1" + "");
                Log.d("file", bankStatementFile + "");
                getRes = new getResponse(getActivity());

                // aadhar_doc_image1
                getRes.UploadImageGetLoan(uploadCheque2Box2ImageTwo, "" +
                                "" + WebServices.getBaseURL + WebServices.getLoanUploadDocument,
                        "GetLoan_Bank_Statement",
                        AppInfo.ekycApplicationId,
                        reqType);

                break;

            case "BankStatementPdf":
                getLoanDetailsButtonvalidation();

                Log.d("URLDocUpload", WebServices.getBaseURL + WebServices.getLoanUploadDocument + "");
                Log.d("ekycId", AppInfo.ekycApplicationId + "");
                Log.d("documentType", "GetLoan_Cheque1" + "");
                Log.d("file", bankStatementPdf + "");
                Log.d("pdffff", bankStatementPdf + "pdf calll");
                getRes = new getResponse(getActivity());

                getRes.UploadPDFGetLoanDocument(bankStatementPdf, "" +
                                "" + WebServices.getBaseURL + WebServices.getLoanUploadDocument,
                        "GetLoan_Bank_Statement",
                        AppInfo.ekycApplicationId,
                        reqType);

                break;

        }

    }

    private void callEditableForAditionalOne() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_addtional_one);

        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button

        final EditText addNotes = dialog.findViewById(R.id.addNotes);
        final TextView noofText = dialog.findViewById(R.id.noofText);

        userDocuments userDocuments = FinalApplicationForm.getUserDocuments();

        if(AppInfo.IsReadonlyMode) {

            addNotes.setEnabled(false);
        }else {

            addNotes.setEnabled(true);
        }

//        System.out.println("Lenght_of", ">>>" + userDocuments.getAdditionalDescription1().length());


        RelativeLayout dialogButton = dialog.findViewById(R.id.lSumbot);
        // if button is clicked, close the custom dialog

        addNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    noofText.setText(s.length() + "/250");

                } else {
                    noofText.setText("0/250");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = addNotes.getText().toString();

                if (note.length() > 0) {
                    dialog.dismiss();
                    uploadDocuments(note);

                } else {
                    Toast.makeText(getActivity(), "Please enter description", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();

    }

    private void uploadDocuments(String note) {
        getRes = new getResponse(getActivity());
        getRes.Uploadadditional(tempHoldImageView,
                "" + WebServices.getBaseURL + WebServices.uploadAdditionalOneURL + "?documentType=BrokerageDocument",
                "BrokerageDocument", note,
                AppInfo.ekycApplicationId);
        brpkrageDescription = note;
        brokrafeNotes.setText(note);

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 100;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }

    private Bitmap decodeUriTemp(Uri selectedImage) throws FileNotFoundException {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        //The new size we want to scale to
        final int REQUIRED_SIZE = 500;

        //Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }

    private long getFileSizeinKb(Uri uri) {
        File f = new File(getActivity().getCacheDir(), "TempHold");
        try {

            tempHoldImageView.buildDrawingCache();
            Bitmap bmap = tempHoldImageView.getDrawingCache();
            Bitmap bitmap = null;
            bitmap = decodeUriTemp(uri);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            try{
                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                System.out.println("fileSize>>>." + f.length() + "");
            }catch (IOException e) {
                e.printStackTrace();
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(f != null){
            if(f.length() > 700000) {
                System.out.println("BeforeLength<<<" + f.length() + "");
                File compressedImageFile = null;
                try {
                    compressedImageFile = new Compressor(getActivity()).compressToFile(f);
                    tempHoldImageView.setImageBitmap(new Compressor(getActivity()).compressToBitmap(f));
                    System.out.println("AfterLength<<<" + compressedImageFile.length() + "");
                    return compressedImageFile.length();
                }catch (IOException e) {
                    return f.length();
                }
            }else{
                return f.length(); }
        }else{
            return 0;
        }
    }

    private void beginCrop(Uri photoURI) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), ".jpg"));
        Crop.of(photoURI, destination).withMaxSize(2000, 2000).start(getActivity(), ReviewFragment.this);
        //Crop.of(source, destination).withAspect(480, 320).withMaxSize(1024,720).start(getActivity(), user_FileUpload.this);

    }

    public boolean checkValidUri(Uri uri) {

        ImageView img = new ImageView(getActivity());
        img.setImageURI(uri);
        return img.getDrawable() != null;

    }

    // Callback listener functions
    public void onSigningSuccess(String documentId) {


    }

    // 5662 1282 7890
    public void onSigningFailure(String documentId, int code, String response) {
        //Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    /**
     * method to initialize the listeners
     */

    private void initListenersAdditionalDoc() {
        // register the listener for button click

        // Capturing the callbacks for bottom sheet
        bottomSheetBehaviorAdditionDoc.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else {

                }

                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_SETTLING");
                        break;
                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });


        bottomSheetBehaviorAddNominee.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else {

                }

                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_SETTLING");
                        break;
                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetBehaviorAddNominees.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else {

                }

                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_SETTLING");
                        break;
                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });


    }

    /**
     * method to initialize the listeners
     */

    private void initListenersBrockrage() {
        // register the listener for button click

        // Capturing the callbacks for bottom sheet
        bottomSheetBehaviorBrockrage.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else {

                }

                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        System.out.println("Bottom Sheet Behaviour" + "STATE_SETTLING");
                        break;
                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });


    }

    public void fillEquityForm() {

        try {
            HashMap<String, Object> parameters;
            getResponse getRes = new getResponse(getActivity());
//http://183.182.86.91:8096/api/eKycApplication/fillEquityForm?ekycApplicationId=609
            parameters = new HashMap<String, Object>();
            getRes.getResponse(WebServices.getBaseURL + "/eKycApplication" +
                            WebServices.getEquityFormPDFURL + "?ekycApplicationId=" + AppInfo.ekycApplicationId,
                    "fillEquityFormNew",
                    parameters);

        } catch (Exception ex) {

            System.out.println("ExceptionOnCall" + ex.getMessage());
        }


    }

    public void fillEquityFormForEmudra() {

        try {
            HashMap<String, Object> parameters;
            getResponse getRes = new getResponse(getActivity());
//http://183.182.86.91:8096/api/eKycApplication/fillEquityForm?ekycApplicationId=609
            parameters = new HashMap<String, Object>();
            getRes.getResponse(WebServices.getBaseURL + "/eKycApplication" +
                            WebServices.getEquityFormPDFURL + "?ekycApplicationId=" + AppInfo.ekycApplicationId,
                    "fillEquityFormEMudra",
                    parameters);

        } catch (Exception ex) {

            System.out.println("ExceptionOnCall" + ex.getMessage());
        }


    }

    public void fillEquityFormforPhysical() {

        try {
            HashMap<String, Object> parameters;
            getResponse getRes = new getResponse(getActivity());
//http://183.182.86.91:8096/api/eKycApplication/fillEquityForm?ekycApplicationId=609
            parameters = new HashMap<String, Object>();
            getRes.getResponse(WebServices.getBaseURL + "/eKycApplication" +
                            WebServices.getEquityFormPDFURL + "?ekycApplicationId=" + AppInfo.ekycApplicationId,
                    "fillEquityFormforPhysical",
                    parameters);

        } catch (Exception ex) {

            System.out.println("ExceptionOnCall" + ex.getMessage());
        }
    }

    public void eSignDigio() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }

                //  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                String extStorageDirectory = "";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    System.out.println("pdf_download_code : " + "in android 11");
                    extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                } else {
                    System.out.println("pdf_download_code : " + "in android 10");
                    extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                }
                File folder = new File(extStorageDirectory, "EquityForm");

                File pdfFile = null;
                pdfFile = new File(folder, filledFormFileName);
                System.out.println("AMAN_PDF" + pdfFile.toString());

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.pdf_dialog);

                PDFView pdfView = dialog.findViewById(R.id.pdfView);
                pdf_terms_check = dialog.findViewById(R.id.pdf_terms_check);

                if (pdfFile != null) {
                    pdfView.fromFile(pdfFile)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .enableAnnotationRendering(true)
                            .defaultPage(0)
                            .load();
                }


                TextView menu1 = dialog.findViewById(R.id.menu1);
                TextView menu2 = dialog.findViewById(R.id.menu2);

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        if (File_download_dialog != null) {
                            if (File_download_dialog.isShowing())
                                File_download_dialog.dismiss();
                        }

                    }
                });

                menu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pdf_terms_check.isChecked()) {
                            dialog.dismiss();
                            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(WHITE)

                                    .fadeColor(DKGRAY).build();
                            acProgressFlower.show();

                            //generateesign();

                            checkeSignStatus();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Please check the checkbox")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                });


                if (dialog != null) {
                    dialog.show();
                }

            }
        }, 1000);


    }

    private void calluploadondigio() {
        //

        try {
            HashMap<String, Object> parameters;
            getResponse getRes = new getResponse(getActivity());
//http://54.80.63.129/api/eKycApplication/UploadOnDigio?ekycApplicationId=743
            parameters = new HashMap<String, Object>();
            getRes.getResponse(WebServices.getBaseURL + "/eKycApplication" +
                            WebServices.getUploadOnDigio + "?ekycApplicationId=" + AppInfo.ekycApplicationId,
                    "UploadOnDigio",
                    parameters);

        } catch (Exception ex) {

            System.out.println("ExceptionOnCall" + ex.getMessage());
        }

    }

    public void getSignedDocument() {

        getRes.getResponse(WebServices.getDigioDocument, "GetDigioDocument", null);

    }

    public void getBrokerageData() {

        try {
            System.out.println("getBrokerageData" + WebServices.getBaseURL + WebServices.getBrokerageByekycId + "?ekycAppId=" + AppInfo.ekycApplicationId);
            parameters = new HashMap<String, Object>();
            getRes.getResponse(WebServices.getBaseURL + WebServices.getBrokerageByekycId + "?ekycAppId=" + AppInfo.ekycApplicationId,
                    "getBrokerageByekycId",
                    parameters);

        }catch (Exception ex) {
            System.out.println("ExceptionOnCall" + ex.getMessage());
        }
    }

    public void getBrokeragefromMaster() {
        try {
            //http://54.80.63.129/api/Masters/GetBrokerageByCompanyId?CompanyId=2
            parameters = new HashMap<String, Object>();
            getRes.getResponse(WebServices.getBaseURL + WebServices.getBrokerageByCompanyId + "?CompanyId=1",
                    "getBrokerageByCompanyId",
                    parameters);
        } catch (Exception ex) {
            System.out.println("ExceptionOnCall" + ex.getMessage());
        }
    }

    public void setBrokerage() {
        hideKeyboardReview(getActivity());
        System.out.println("getBrokerageByekycId : " + "SaveBrokerage" + "  in setBrokerage ");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BuildConfig.FLAVOR.equalsIgnoreCase("Swastika")) {
                    //Do something after 100ms
                    if (stock_delivery.getText().toString().length() == 0 || Float.parseFloat(stock_delivery.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (stock_intraday.getText().toString().length() == 0 || Float.parseFloat(stock_intraday.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (derivatives_futures.getText().toString().length() == 0 || Float.parseFloat(derivatives_futures.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (derivatives_options.getText().toString().length() == 0 || Float.parseFloat(derivatives_options.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (currency_derivatives_futures.getText().toString().length() == 0 || Float.parseFloat(currency_derivatives_futures.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (currency_derivatives_options.getText().toString().length() == 0 || Float.parseFloat(currency_derivatives_options.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (commodity_derivatives_futures.getText().toString().length() == 0 || Float.parseFloat(commodity_derivatives_futures.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else if (commodity_derivatives_options.getText().toString().length() == 0 || Float.parseFloat(commodity_derivatives_options.getText().toString()) <= 0) {
                        Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                    } else {

                        acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(WHITE)

                                .fadeColor(DKGRAY).build();

                        if (acProgressFlower != null) {
                            acProgressFlower.show();
                        }

                        bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);

                        try {


                            //sendbrockragedata

                            HashMap<String, Object> parameters;
                            getResponse getRes = new getResponse(getActivity());

                            parameters = new HashMap<String, Object>();
                            parameters.put("stockDeliver", stock_delivery.getText().toString());
                            parameters.put("stockIntraday", stock_intraday.getText().toString());
                            parameters.put("der_futures", derivatives_futures.getText().toString());
                            parameters.put("der_options", derivatives_options.getText().toString());
                            parameters.put("cur_future", currency_derivatives_futures.getText().toString());
                            parameters.put("cur_options", currency_derivatives_options.getText().toString());
                            parameters.put("com_futures", commodity_derivatives_futures.getText().toString());
                            parameters.put("com_options", commodity_derivatives_options.getText().toString());
                            parameters.put("com_options_mod", commodity_derivatives_options_MODULE_NO);
                            parameters.put("com_future_mod", commodity_derivatives_futures_MODULE_NO);
                            parameters.put("cur_future_mod", currency_derivatives_futures_MODULE_NO);
                            parameters.put("cur_options_mod", currency_derivatives_options_MODULE_NO);
                            parameters.put("der_future_mod", derivative_future_MODULE_NO);
                            parameters.put("der_options_mod", derivatives_options_MODULE_NO);
                            parameters.put("stock_delivery_mod", Cash_Delivery_MODULE_NO);
                            parameters.put("stock_intraday_mod", Cash_intraday_MODULE_NO);
                            parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                            parameters.put("contract_note", "");
                            parameters.put("IsPremium", true);
                            parameters.put("turnOver", turnOverEdit.getText().toString());

                            System.out.println("deravaative valuess==" + derivatives_options.getText().toString() + " module no." + derivatives_options_MODULE_NO);

                            System.out.println("derivatives_options_MODULE_NO" + derivatives_options_MODULE_NO);
                            System.out.println("getBrokerageByekycId" + parameters.toString());

                            getRes.getResponseFromURL(WebServices.getBaseURL + WebServices.addBrokerage,
                                    "addBrokerage",
                                    parameters, "POST");

                        } catch (Exception ex) {

                            System.out.println("ExceptionOnCall" + ex.getMessage());
                        }
                    }
                } else if (BuildConfig.FLAVOR.equalsIgnoreCase("Tradingo")) {
                    if (premiumBrokerage.getVisibility() == View.VISIBLE) {
                        //Do something after 100ms
                        if (stock_delivery.getText().toString().length() == 0 || Float.parseFloat(stock_delivery.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (stock_intraday.getText().toString().length() == 0 || Float.parseFloat(stock_intraday.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (derivatives_futures.getText().toString().length() == 0 || Float.parseFloat(derivatives_futures.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (derivatives_options.getText().toString().length() == 0 || Float.parseFloat(derivatives_options.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (currency_derivatives_futures.getText().toString().length() == 0 || Float.parseFloat(currency_derivatives_futures.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (currency_derivatives_options.getText().toString().length() == 0 || Float.parseFloat(currency_derivatives_options.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (commodity_derivatives_futures.getText().toString().length() == 0 || Float.parseFloat(commodity_derivatives_futures.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (commodity_derivatives_options.getText().toString().length() == 0 || Float.parseFloat(commodity_derivatives_options.getText().toString()) <= 0) {

                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else {

                            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(WHITE)

                                    .fadeColor(DKGRAY).build();
                            acProgressFlower.show();

                            bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);

                            try {
                                HashMap<String, Object> parameters;
                                getResponse getRes = new getResponse(getActivity());

                                parameters = new HashMap<String, Object>();
                                parameters.put("stockDeliver", stock_delivery.getText().toString());
                                parameters.put("stockIntraday", stock_intraday.getText().toString());
                                parameters.put("der_futures", derivatives_futures.getText().toString());
                                parameters.put("der_options", derivatives_options.getText().toString());
                                parameters.put("cur_future", currency_derivatives_futures.getText().toString());
                                parameters.put("cur_options", currency_derivatives_options.getText().toString());
                                parameters.put("com_futures", commodity_derivatives_futures.getText().toString());
                                parameters.put("com_options", commodity_derivatives_options.getText().toString());
                                parameters.put("com_options_mod", commodity_derivatives_options_MODULE_NO);
                                parameters.put("com_future_mod", commodity_derivatives_futures_MODULE_NO);
                                parameters.put("cur_future_mod", currency_derivatives_futures_MODULE_NO);
                                parameters.put("cur_options_mod", currency_derivatives_options_MODULE_NO);
                                parameters.put("der_future_mod", derivative_future_MODULE_NO);
                                parameters.put("der_options_mod", derivatives_options_MODULE_NO);
                                parameters.put("stock_delivery_mod", Cash_Delivery_MODULE_NO);
                                parameters.put("stock_intraday_mod", Cash_intraday_MODULE_NO);
                                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                                parameters.put("contract_note", "");
                                parameters.put("IsPremium", true);
                                parameters.put("turnOver", turnOverEdit.getText().toString());
                                System.out.println("getBrokerageByekycId" + parameters.toString());

                                getRes.getResponseFromURL(WebServices.getBaseURL +
                                                WebServices.addBrokerage,
                                        "addBrokerage",
                                        parameters, "POST");

                            } catch (Exception ex) {

                                System.out.println("ExceptionOnCall" + ex.getMessage());
                            }
                        }
                    } else if (discountBrokerage.getVisibility() == View.VISIBLE) {
                        if (discountstockdelivery.getText().toString().length() == 0 || Float.parseFloat(discountstockdelivery.getText().toString()) < 0) {

                            Log.d("ReviewFragment : ", "discountstockdelivery " + discountstockdelivery.getText().toString());
                            Log.d("ReviewFragment : ", "discountstockdelivery " + discountstockdelivery.getText().toString().length());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountstockintraday.getText().toString().length() == 0 || Float.parseFloat(discountstockintraday.getText().toString()) <= 0) {

                            Log.d("ReviewFragment : ", "discountstockintraday " + discountstockintraday.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountderivativesfutures.getText().toString().length() == 0 || Float.parseFloat(discountderivativesfutures.getText().toString()) <= 0) {
                            Log.d("ReviewFragment : ", "discountderivativesfutures " + discountderivativesfutures.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountderivativesoptions.getText().toString().length() == 0 || Float.parseFloat(discountderivativesoptions.getText().toString()) <= 0) {

                            Log.d("ReviewFragment : ", "discountderivativesoptions " + discountderivativesoptions.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountcurrencyderivativesfutures.getText().toString().length() == 0 || Float.parseFloat(discountcurrencyderivativesfutures.getText().toString()) <= 0) {

                            Log.d("ReviewFragment : ", "discountcurrencyderivativesfutures " + discountcurrencyderivativesfutures.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountcurrencyderivativesoptions.getText().toString().length() == 0 || Float.parseFloat(discountcurrencyderivativesoptions.getText().toString()) <= 0) {

                            Log.d("ReviewFragment : ", "discountcurrencyderivativesoptions " + discountcurrencyderivativesoptions.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountcommodityderivativesfutures.getText().toString().length() == 0 || Float.parseFloat(discountcommodityderivativesfutures.getText().toString()) <= 0) {

                            Log.d("ReviewFragment : ", "discountcommodityderivativesfutures " + discountcommodityderivativesfutures.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();

                        } else if (discountcommodityderivativesoptions.getText().toString().length() == 0 || Float.parseFloat(discountcommodityderivativesoptions.getText().toString()) <= 0) {

                            Log.d("ReviewFragment : ", "discountcommodityderivativesoptions " + discountcommodityderivativesoptions.getText().toString());
                            Toast.makeText(getActivity(), "Brokerage cannot be blank or zero", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("ReviewFragment : ", "else ");

                            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(WHITE)
                                    .fadeColor(DKGRAY).build();
                            acProgressFlower.show();
                            bottomSheetBehaviorBrockrage.setState(BottomSheetBehavior.STATE_HIDDEN);

                            try {
                                HashMap<String, Object> parameters;
                                getResponse getRes = new getResponse(getActivity());

                                parameters = new HashMap<String, Object>();
                                parameters.put("stockDeliver", discountstockdelivery.getText().toString());
                                parameters.put("stockIntraday", discountstockintraday.getText().toString());
                                parameters.put("der_futures", discountderivativesfutures.getText().toString());
                                parameters.put("der_options", discountderivativesoptions.getText().toString());
                                parameters.put("cur_future", discountcurrencyderivativesfutures.getText().toString());
                                parameters.put("cur_options", discountcurrencyderivativesoptions.getText().toString());
                                parameters.put("com_futures", discountcommodityderivativesfutures.getText().toString());
                                parameters.put("com_options", discountcommodityderivativesoptions.getText().toString());
                                parameters.put("ekycApplicationId", AppInfo.ekycApplicationId);
                                parameters.put("contract_note", "");
                                parameters.put("IsPremium", false);
                                parameters.put("turnOver", turnOverEdit.getText().toString());
                                System.out.println("getBrokerageByekycId" + parameters.toString());

                                getRes.getResponseFromURL(WebServices.getBaseURL +
                                                WebServices.addBrokerage,
                                        "addBrokerage",
                                        parameters, "POST");

                            } catch (Exception ex) {

                                System.out.println("ExceptionOnCall" + ex.getMessage());
                            }
                        }
                    }
                }
            }
        }, 500);

    }


    public boolean isLessThanRequired() {
        boolean lessthan = false;
        try {
            double value = Double.parseDouble(stock_delivery_value);
            System.out.println("isLessThanRequired :" + "stock_delivery_value>>" + value + " " + stock_delivery.getText().toString());
            if (value > Double.parseDouble(stock_delivery.getText().toString())) {
                lessthan = true;
            }

            value = Double.parseDouble(stock_intraday_value);
            System.out.println("isLessThanRequired :" + "stock_intraday_value>>" + value + " " + stock_intraday.getText().toString());
            if (value > Double.parseDouble(stock_intraday.getText().toString())) {

                lessthan = true;

            }

            value = Double.parseDouble(derivatives_futures_value);
            System.out.println("isLessThanRequired :" + "derivatives_futures" + value + " " + derivatives_futures.getText().toString());
            if (value > Double.parseDouble(derivatives_futures.getText().toString())) {
                lessthan = true;

            }
            value = Double.parseDouble(derivatives_options_value);
            System.out.println("isLessThanRequired :" + "derivatives_options" + value + " " + derivatives_options.getText().toString());
            if (value > Double.parseDouble(derivatives_options.getText().toString())) {
                lessthan = true;
            }
            value = Double.parseDouble(currency_derivatives_futures_value);
            System.out.println("isLessThanRequired :" + "currency_deri_futures" + value + " " + currency_derivatives_futures.getText().toString());
            if (value > Double.parseDouble(currency_derivatives_futures.getText().toString())) {

                lessthan = true;

            }

            value = Double.parseDouble(currency_derivatives_options_value);
            System.out.println("isLessThanRequired :" + "currency_deri_options" + value + " " + currency_derivatives_options.getText().toString());
            if (value > Double.parseDouble(currency_derivatives_options.getText().toString())) {

                lessthan = true;
            }
            value = Double.parseDouble(commodity_derivatives_futures_value);
            System.out.println("isLessThanRequired :" + "commodity_deri_futures" + value + " " + commodity_derivatives_futures.getText().toString());
            if (value > Double.parseDouble(commodity_derivatives_futures.getText().toString())) {
                lessthan = true;
            }
            value = Double.parseDouble(commodity_derivatives_options_value);
            System.out.println("isLessThanRequired :" + "commodity_deri_options" + value + " " + commodity_derivatives_options.getText().toString());
            if (value > Double.parseDouble(commodity_derivatives_options.getText().toString())) {
                lessthan = true;
            }
            if (lessthan) {

                uploadAdditionalDocLayout.setVisibility(View.VISIBLE);

            } else {

                uploadAdditionalDocLayout.setVisibility(View.GONE);
            }
            System.out.println("isLessThanRequired :" + "Less than value " + lessthan + "");
            System.out.println("isLessThanRequired :" + "ReturingLessThan" + lessthan + "");
        } catch (Exception e) {
            System.out.println("isLessThanRequired :" + "Exception " + e.getMessage() + "");
        }
        return lessthan;
    }


    public void setUpAdditionalUI() {
        submit_btn_additional_detail = getActivity().findViewById(R.id.submit_btn_additional_detail);
        brokTxt = getActivity().findViewById(R.id.brokTxt);
        brokTxt = getActivity().findViewById(R.id.brokTxt);

        subBroker_form_layout = getActivity().findViewById(R.id.subBroker_form_layout);
        subBroker_form_layout.setVisibility(View.GONE);

        nominee_input_box = getActivity().findViewById(R.id.nominee_input_box);
        nominee_input_box_new = getActivity().findViewById(R.id.nominee_input_box_new);
        nominee_address_different = getActivity().findViewById(R.id.nominee_address_different);
        nominee_address_different_new = getActivity().findViewById(R.id.nominee_address_different_new);

        //,

        nominee_address_different_news = getActivity().findViewById(R.id.nominee_address_different_news);

        nominee_address_different_news2 = getActivity().findViewById(R.id.nominee_address_different_news2);
        nominee_address_different_news3 = getActivity().findViewById(R.id.nominee_address_different_news3);


        lMainGardian = getActivity().findViewById(R.id.lMainGardian);
        lMainGardian_new = getActivity().findViewById(R.id.lMainGardian_new);
        incomeDocLayout = getActivity().findViewById(R.id.incomeDocLayout);
        llSuporttivedoc = getActivity().findViewById(R.id.llSuporttivedoc);
        llSuporttivedoc.setVisibility(View.GONE);

        lMainGardian.setVisibility(View.GONE);
        lMainGardian_new.setVisibility(View.GONE);
        nominee_input_box.setVisibility(View.GONE);
        nominee_input_box_new.setVisibility(View.GONE);
        nominee_address_different.setVisibility(View.GONE);
        nominee_address_different_new.setVisibility(View.GONE);
        nominee_address_different_news.setVisibility(View.VISIBLE);

        is_a_us_person = getActivity().findViewById(R.id.is_a_us_person);
        specify_country_residence = getActivity().findViewById(R.id.specify_country_residence);
        sebi_registration_group = getActivity().findViewById(R.id.sebi_registration_group);
        specify_country_citizenhip = getActivity().findViewById(R.id.specify_country_citizenhip);
        past_actions = getActivity().findViewById(R.id.past_actions);
        sub_broker = getActivity().findViewById(R.id.sub_broker);
        review_contract = getActivity().findViewById(R.id.review_contract);
        RGaccount = getActivity().findViewById(R.id.RGaccount);
        review_delivery = getActivity().findViewById(R.id.review_delivery);
        shareEmail = getActivity().findViewById(R.id.shareEmail);
        recieve_annual_report = getActivity().findViewById(R.id.recieve_annual_report);
        receive_DP_accounts = getActivity().findViewById(R.id.receive_DP_accounts);
        address_of_nominee = getActivity().findViewById(R.id.address_of_nominee);
        address_of_nominee_new = getActivity().findViewById(R.id.address_of_nominee_new);

        declare_mobile_number_belong_to = getActivity().findViewById(R.id.declare_mobile_number_belong_to);
        declare_email_belongs_to = getActivity().findViewById(R.id.declare_email_belongs_to);
        nominee_identification_option = getActivity().findViewById(R.id.nominee_identification_option);
        nominee_identification_option_new = getActivity().findViewById(R.id.nominee_identification_option_new);
        avail_transaction_using_secured = getActivity().findViewById(R.id.avail_transaction_using_secured);

        instruction_dp_to_recive_every_cradit = getActivity().findViewById(R.id.instruction_dp_to_recive_every_cradit);
        instruction_dp_to_accept_all_pledge = getActivity().findViewById(R.id.instruction_dp_to_accept_all_pledge);
        bank_account_as_given_below_through_ECS = getActivity().findViewById(R.id.bank_account_as_given_below_through_ECS);
        basic_services_demat_account_facility = getActivity().findViewById(R.id.basic_services_demat_account_facility);

        make_nomination = getActivity().findViewById(R.id.make_nomination);
        make_nomination_new = getActivity().findViewById(R.id.make_nomination_new);
        radioGroupdurgesh = getActivity().findViewById(R.id.radioGroupdurgesh);


        is_a_us_person_NO = getActivity().findViewById(R.id.is_a_us_person_NO);
        specify_country_residence_INDIA = getActivity().findViewById(R.id.specify_country_residence_INDIA);
        specify_country_residence_OTHER = getActivity().findViewById(R.id.specify_country_residence_OTHER);
        specify_country_citizenhip_INDIA = getActivity().findViewById(R.id.specify_country_citizenhip_INDIA);
        specify_country_citizenhip_OTHER = getActivity().findViewById(R.id.specify_country_citizenhip_OTHER);
        past_actions_NO = getActivity().findViewById(R.id.past_actions_NO);
        past_actions_YES = getActivity().findViewById(R.id.past_actions_YES);
        sub_broker_NO = getActivity().findViewById(R.id.sub_broker_NO);
        sub_broker_YES = getActivity().findViewById(R.id.sub_broker_YES);
        sebi_registration_group_NO = getActivity().findViewById(R.id.sebi_registration_group_NO);
        sebi_registration_group_YES = getActivity().findViewById(R.id.sebi_registration_group_YES);

        radio_cdsl = getActivity().findViewById(R.id.radio_cdsl);
        radio_nsdl = getActivity().findViewById(R.id.radio_nsdl);
        radio_other = getActivity().findViewById(R.id.radio_other);
        review_contract_electronically = getActivity().findViewById(R.id.review_contract_electronically);

        review_contract_physically = getActivity().findViewById(R.id.review_contract_physically);
        review_delivery_NO = getActivity().findViewById(R.id.review_delivery_NO);
        review_delivery_YES = getActivity().findViewById(R.id.review_delivery_YES);
        shareEmail_YES = getActivity().findViewById(R.id.shareEmail_YES);
        shareEmail_NO = getActivity().findViewById(R.id.shareEmail_NO);
        recieve_annual_report_electronically = getActivity().findViewById(R.id.recieve_annual_report_electronically);
        recieve_annual_report_physically = getActivity().findViewById(R.id.recieve_annual_report_physically);
        recieve_annual_report_both = getActivity().findViewById(R.id.recieve_annual_report_both);
        receive_DP_accounts_as_per_sebi = getActivity().findViewById(R.id.receive_DP_accounts_as_per_sebi);
        receive_DP_accounts_monthly = getActivity().findViewById(R.id.receive_DP_accounts_monthly);
        receive_DP_accounts_forthnightly = getActivity().findViewById(R.id.receive_DP_accounts_forthnightly);
        receive_DP_accounts_weekly = getActivity().findViewById(R.id.receive_DP_accounts_weekly);
        declare_mobile_number_belong_to_self = getActivity().findViewById(R.id.declare_mobile_number_belong_to_self);
        declare_mobile_number_belong_to_spouse = getActivity().findViewById(R.id.declare_mobile_number_belong_to_spouse);
        declare_mobile_number_belong_to_child = getActivity().findViewById(R.id.declare_mobile_number_belong_to_child);
        declare_mobile_number_belong_to_parant = getActivity().findViewById(R.id.declare_mobile_number_belong_to_parant);
        declare_mobile_email_belongs_to_self = getActivity().findViewById(R.id.declare_mobile_email_belongs_to_self);
        declare_mobile_email_belongs_to_spouse = getActivity().findViewById(R.id.declare_mobile_email_belongs_to_spouse);
        declare_mobile_email_belongs_to_child = getActivity().findViewById(R.id.declare_mobile_email_belongs_to_child);
        declare_mobile_email_belongs_to_parent = getActivity().findViewById(R.id.declare_mobile_email_belongs_to_parent);
        avail_transaction_using_secured_YES = getActivity().findViewById(R.id.avail_transaction_using_secured_YES);
        avail_transaction_using_secured_NO = getActivity().findViewById(R.id.avail_transaction_using_secured_NO);

        instruction_dp_to_recive_every_cradit_yes = getActivity().findViewById(R.id.instruction_dp_to_recive_every_cradit_yes);
        instruction_dp_to_recive_every_cradit_NO = getActivity().findViewById(R.id.instruction_dp_to_recive_every_cradit_no);

        instruction_dp_to_accept_all_pledge_YES = getActivity().findViewById(R.id.instruction_dp_to_accept_all_pledge_yes);
        instruction_dp_to_accept_all_pledge_NO = getActivity().findViewById(R.id.instruction_dp_to_accept_all_pledge_no);

        bank_account_as_given_below_through_ECS_YES = getActivity().findViewById(R.id.bank_account_as_given_below_through_ECS_yes);
        bank_account_as_given_below_through_ECS_NO = getActivity().findViewById(R.id.bank_account_as_given_below_through_ECS_no);

        basic_services_demat_account_facility_YES = getActivity().findViewById(R.id.basic_services_demat_account_facility_yes);
        basic_services_demat_account_facility_NO = getActivity().findViewById(R.id.basic_services_demat_account_facility_no);

        make_nomination_NO = getActivity().findViewById(R.id.make_nomination_NO);
        make_nomination_NO_new = getActivity().findViewById(R.id.make_nomination_NO_new);
        make_nomination_YES = getActivity().findViewById(R.id.make_nomination_YES);
        make_nomination_YES_new = getActivity().findViewById(R.id.make_nomination_YES_new);
        radionobutton_no = getActivity().findViewById(R.id.radionobutton_no);
        radioyesbutton_yes = getActivity().findViewById(R.id.radioyesbutton_yes);

        //relationshipapplicant=getActivity().findViewById(R.id.relationshipapplicant);

        remove_nominee = getActivity().findViewById(R.id.remove_nominee);
        add_nominee = getActivity().findViewById(R.id.add_nominee);
        address_of_nominee_same_as_application = getActivity().findViewById(R.id.address_of_nominee_same_as_application);
        address_of_nominee_same_as_application_new = getActivity().findViewById(R.id.address_of_nominee_same_as_application_new);


        address_of_nominee_different_from_application = getActivity().findViewById(R.id.address_of_nominee_different_from_application);
        address_of_nominee_different_from_application_new = getActivity().findViewById(R.id.address_of_nominee_different_from_application_new);


        nominee_identification_option_photograph = getActivity().findViewById(R.id.nominee_identification_option_photograph);
        nominee_identification_option_photograph_new = getActivity().findViewById(R.id.nominee_identification_option_photograph_new);
        nominee_identification_option_pan = getActivity().findViewById(R.id.nominee_identification_option_pan);
        nominee_identification_option_pan_new = getActivity().findViewById(R.id.nominee_identification_option_pan_new);
        nominee_identification_option_address_proof = getActivity().findViewById(R.id.nominee_identification_option_address_proof);
        nominee_identification_option_address_proof_new = getActivity().findViewById(R.id.nominee_identification_option_address_proof_new);
        nominee_identification_option_proof_of_identity = getActivity().findViewById(R.id.nominee_identification_option_proof_of_identity);
        nominee_identification_option_proof_of_identity_new = getActivity().findViewById(R.id.nominee_identification_option_proof_of_identity_new);
        nominee_identification_option_saving_bank = getActivity().findViewById(R.id.nominee_identification_option_saving_bank);
        nominee_identification_option_saving_bank_new = getActivity().findViewById(R.id.nominee_identification_option_saving_bank_new);
        nominee_identification_option_demat_account = getActivity().findViewById(R.id.nominee_identification_option_demat_account);
        nominee_identification_option_demat_account_new = getActivity().findViewById(R.id.nominee_identification_option_demat_account_new);
        guardian_photograph = getActivity().findViewById(R.id.guardian_photograph);
        guardian_photograph_new = getActivity().findViewById(R.id.guardian_photograph_new);
        guardian_pan = getActivity().findViewById(R.id.guardian_pan);
        guardian_pan_new = getActivity().findViewById(R.id.guardian_pan_new);
        guardian_address_proof = getActivity().findViewById(R.id.guardian_address_proof);
        guardian_address_proof_new = getActivity().findViewById(R.id.guardian_address_proof_new);
        guardian_proof_of_identity = getActivity().findViewById(R.id.guardian_proof_of_identity);
        guardian_proof_of_identity_new = getActivity().findViewById(R.id.guardian_proof_of_identity_new);
        guardian_saving_bank_account = getActivity().findViewById(R.id.guardian_saving_bank_account);
        guardian_saving_bank_account_new = getActivity().findViewById(R.id.guardian_saving_bank_account_new);
        guardian_demat_account = getActivity().findViewById(R.id.guardian_demat_account);
        guardian_demat_account_new = getActivity().findViewById(R.id.guardian_demat_account_new);


        past_action_specification_edt = getActivity().findViewById(R.id.past_action_specification_edt);
        past_action_specification_edt.setVisibility(View.GONE);

      sub_broker_name_edt = getActivity().findViewById(R.id.sub_broker_name_edt);
        stock_broker_name = getActivity().findViewById(R.id.stock_broker_name);
        sub_broker_authorised_person_edt = getActivity().findViewById(R.id.sub_broker_authorised_person_edt);
        sub_broker_exchange_name_edt = getActivity().findViewById(R.id.sub_broker_exchange_name_edt);
        sub_broker_client_code_edt = getActivity().findViewById(R.id.sub_broker_client_code_edt);
        sub_broker_dues_edt = getActivity().findViewById(R.id.sub_broker_dues_edt);
        sub_broker_exchange_edt = getActivity().findViewById(R.id.sub_broker_exchange_edt);
        sebi_registration_group_Number_edt = getActivity().findViewById(R.id.sebi_registration_group_Number_edt);
        sebi_registration_group_Number_edt.setVisibility(View.GONE);
        nominee_Name_edt = getActivity().findViewById(R.id.nominee_Name_edt);
        edt_firstnominee = getActivity().findViewById(R.id.edt_firstnominee);

        edtpercentagesharing = getActivity().findViewById(R.id.edtpercentagesharing);
        edtpercentagesharing.setFilters(new InputFilter[]{ new InputFilterMinMax("1.00", "100.00"),new InputFilter.LengthFilter(5) {
                }
                }
        );
        edt_firstnominee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    isAllFieldsChecked=true;
                }else {
                    isAllFieldsChecked=false;
                } }
        });

        nominee_Share_edt = getActivity().findViewById(R.id.nominee_Share_edt);
        nominee_Share_edt_new = getActivity().findViewById(R.id.nominee_Share_edt_new);
        nominee_address_edt = getActivity().findViewById(R.id.nominee_address_edt);
        nominee_address_edt_new = getActivity().findViewById(R.id.nominee_address_edt_new);
        nominee_pincode_edt = getActivity().findViewById(R.id.nominee_pincode_edt);
        nominee_pincode_edt_new = getActivity().findViewById(R.id.nominee_pincode_edt_new);
        nominee_city_edt = getActivity().findViewById(R.id.nominee_city_edt);
        nominee_city_edt_new = getActivity().findViewById(R.id.nominee_city_edt_new);
        nominee_relationship_edt = getActivity().findViewById(R.id.nominee_relationship_edt);
        nominee_relationship_edt_new = getActivity().findViewById(R.id.nominee_relationship_edt_new);
        nominee_dob_edt = getActivity().findViewById(R.id.nominee_dob_edt);
        nominee_dob_edt_new = getActivity().findViewById(R.id.nominee_dob_edt_new);
        nominee_mobile_number_edt = getActivity().findViewById(R.id.nominee_mobile_number_edt);
        nominee_mobile_number_edt_new = getActivity().findViewById(R.id.nominee_mobile_number_edt_new);
        //edittextpincode = getActivity().findViewById(R.id.edittextpincode);
        nominee_email_edt = getActivity().findViewById(R.id.nominee_email_edt);
        nominee_email_edt_new = getActivity().findViewById(R.id.nominee_email_edt_new);
        nominee_identification_edt = getActivity().findViewById(R.id.nominee_identification_edt);
        nominee_identification_edt_new = getActivity().findViewById(R.id.nominee_identification_edt_new);
        date_of_birth_guardian_edt = getActivity().findViewById(R.id.date_of_birth_guardian_edt);
        date_of_birth_guardian_edt_new = getActivity().findViewById(R.id.date_of_birth_guardian_edt_new);
        name_of_guardian_edt = getActivity().findViewById(R.id.name_of_guardian_edt);
        name_of_guardian_edt_new = getActivity().findViewById(R.id.name_of_guardian_edt_new);
        address_of_guardian_edt = getActivity().findViewById(R.id.address_of_guardian_edt);
        address_of_guardian_edt_new = getActivity().findViewById(R.id.address_of_guardian_edt_new);
        mobile_number_of_guardian_edt = getActivity().findViewById(R.id.mobile_number_of_guardian_edt);
        mobile_number_of_guardian_edt_new = getActivity().findViewById(R.id.mobile_number_of_guardian_edt_new);
        email_id_of_guardian_edt = getActivity().findViewById(R.id.email_id_of_guardian_edt);
        email_id_of_guardian_edt_new = getActivity().findViewById(R.id.email_id_of_guardian_edt_new);
        relationship_of_guardian_edt = getActivity().findViewById(R.id.relationship_of_guardian_edt);
        relationship_of_guardian_edt_new = getActivity().findViewById(R.id.relationship_of_guardian_edt_new);

        if(AppInfo.IsReadonlyMode) {
            submit_btn_additional_detail.setVisibility(View.GONE);
        }else {
            submit_btn_additional_detail.setVisibility(View.VISIBLE);
        }
        past_actions_NO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!AppInfo.IsReadonlyMode) {
                    if (isChecked) {
                        past_action_specification_edt.setVisibility(View.GONE);
                    } else {
                        past_action_specification_edt.setVisibility(View.VISIBLE);
                    }
                } else {
                    past_actions_NO.setEnabled(false);
                }

            }
        });

        past_actions_YES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    past_action_specification_edt.setVisibility(View.VISIBLE);
                } else {
                    past_action_specification_edt.setVisibility(View.GONE);
                }
            }
        });

        sub_broker_NO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    subBroker_form_layout.setVisibility(View.GONE);
                } else {
                    subBroker_form_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        sub_broker_YES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    subBroker_form_layout.setVisibility(View.VISIBLE);
                } else {
                    subBroker_form_layout.setVisibility(View.GONE);
                }
            }
        });

        make_nomination_NO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    nominee_input_box.setVisibility(View.GONE);
                } else {
                    nominee_input_box.setVisibility(View.VISIBLE);
                }
            }
        });

        make_nomination_NO_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    nominee_input_box_new.setVisibility(View.GONE);

                } else {

                    nominee_input_box_new.setVisibility(View.VISIBLE);

                }
            }
        });

        make_nomination_YES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    nominee_input_box.setVisibility(View.VISIBLE);
                } else {
                    nominee_input_box.setVisibility(View.GONE);
                }
            }
        });

        radioyesbutton_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nominee_yes_or_no="Yes";
                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                    notelayout.setVisibility(View.VISIBLE);
                    rl_addnominelayout.setVisibility(View.VISIBLE);

                }else{
                    nominee_yes_or_no="No";
                    nomineetabslayout.setVisibility(View.GONE);
                    main_nominee_layoutsss.setVisibility(View.GONE);
                    notelayout.setVisibility(View.GONE);
                    rl_addnominelayout.setVisibility(View.GONE);

                }
            }
        });

        radionobutton_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nominee_yes_or_no="No";
                    main_nominee_layoutsss.setVisibility(View.GONE);
                    notelayout.setVisibility(View.GONE);
                    nomineetabslayout.setVisibility(View.GONE);
                    rl_addnominelayout.setVisibility(View.GONE);

                }else{
                    nominee_yes_or_no="Yes";
                    main_nominee_layoutsss.setVisibility(View.VISIBLE);
                    notelayout.setVisibility(View.VISIBLE);
                    rl_addnominelayout.setVisibility(View.VISIBLE);
                    // nomineetabslayout.setVisibility(View.VISIBLE);
                }
            }
        });

        make_nomination_YES_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //  add_remove_layout.setVisibility(View.VISIBLE);
                    nomination_plus_img_new.setVisibility(View.VISIBLE);
                } else {
                    //  add_remove_layout.setVisibility(View.GONE);
                    nomination_plus_img_new.setVisibility(View.VISIBLE);
                }
            }
        });

        address_of_nominee_same_as_application.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nominee_address_different.setVisibility(View.GONE);
                } else {
                    nominee_address_different.setVisibility(View.VISIBLE);
                }
            }
        });


        address_of_nominee_same_as_application_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nominee_address_different_new.setVisibility(View.GONE);
                } else {
                    nominee_address_different_new.setVisibility(View.VISIBLE);
                }
            }
        });

        address_of_nominee_different_from_application.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    nominee_address_different.setVisibility(View.VISIBLE);
                } else {
                    nominee_address_different.setVisibility(View.GONE);
                }
            }
        });


        address_of_nominee_different_from_application_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    nominee_address_different_news.setVisibility(View.VISIBLE);

                }else{
                    nominee_address_different_news.setVisibility(View.GONE);
                }
            }
        });


        sebi_registration_group_NO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    sebi_registration_group_Number_edt.setVisibility(View.GONE);
                } else {
                    sebi_registration_group_Number_edt.setVisibility(View.VISIBLE);
                }

            }
        });


        sebi_registration_group_YES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sebi_registration_group_Number_edt.setVisibility(View.VISIBLE);
                } else {
                    sebi_registration_group_Number_edt.setVisibility(View.GONE);
                }
            }
        });

       /* edittextpincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
*/

        submit_btn_additional_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click on button", Toast.LENGTH_SHORT).show();

                if(!AppInfo.IsReadonlyMode) {

                    getAllRadioButtonvalue();

                    callAdditionaldata();

                }else {
                    if(bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                }
            }
        });
    }

    public void onDelete(View v) {
        System.out.println("ReviewFragment : " + " remove_nominee : on click ");
        nominationsLayout_new.removeView((View) v.getParent());
    }


    private void getAllRadioButtonvalue() {
        AdditionalObj = new JSONObject();
        int selectedId = is_a_us_person.getCheckedRadioButtonId();
        is_a_us_person_YES = getActivity().findViewById(selectedId);

        int resiDenacebtn = specify_country_residence.getCheckedRadioButtonId();
        specify_country_residence_INDIA = getActivity().findViewById(resiDenacebtn);


        //3rd one
        int specify_country_citizen = specify_country_citizenhip.getCheckedRadioButtonId();
        specify_country_citizenhip_INDIA = getActivity().findViewById(specify_country_citizen);


        int past_actions_int = past_actions.getCheckedRadioButtonId();
        past_actions_YES = getActivity().findViewById(past_actions_int);


        int sub_broker_int = sub_broker.getCheckedRadioButtonId();
        sub_broker_YES = getActivity().findViewById(sub_broker_int);


        int review_contract_int = review_contract.getCheckedRadioButtonId();
        review_contract_electronically = getActivity().findViewById(review_contract_int);


        int radio_cdsl_int = RGaccount.getCheckedRadioButtonId();
        radio_cdsl = getActivity().findViewById(radio_cdsl_int);

        int review_delivery_int = review_delivery.getCheckedRadioButtonId();
        review_delivery_YES = getActivity().findViewById(review_delivery_int);


        int recieve_annual_report_int = recieve_annual_report.getCheckedRadioButtonId();
        recieve_annual_report_electronically = getActivity().findViewById(recieve_annual_report_int);

        int receive_DP_accounts_int = receive_DP_accounts.getCheckedRadioButtonId();
        receive_DP_accounts_as_per_sebi = getActivity().findViewById(receive_DP_accounts_int);

        int nominee_int = make_nomination.getCheckedRadioButtonId();
        make_nomination_YES = getActivity().findViewById(nominee_int);

        int nominee_int_new = make_nomination_new.getCheckedRadioButtonId();
        make_nomination_YES_new = getActivity().findViewById(nominee_int_new);


        int nominee_int_newdurgesh = radioGroupdurgesh.getCheckedRadioButtonId();

        radionobutton_no = getActivity().findViewById(nominee_int_newdurgesh);


        radioyesbutton_yes = getActivity().findViewById(nominee_int_newdurgesh);


        int sebi_int = sebi_registration_group.getCheckedRadioButtonId();
        sebi_registration_group_YES = getActivity().findViewById(sebi_int);

        int shareEmail_int = shareEmail.getCheckedRadioButtonId();
        shareEmail_YES = getActivity().findViewById(shareEmail_int);

        int validmbl_int = declare_mobile_number_belong_to.getCheckedRadioButtonId();
        declare_mobile_number_belong_to_self = getActivity().findViewById(validmbl_int);

        int email_belongs_int = declare_email_belongs_to.getCheckedRadioButtonId();
        declare_mobile_email_belongs_to_self = getActivity().findViewById(email_belongs_int);

        int avail_transaction_int = avail_transaction_using_secured.getCheckedRadioButtonId();
        avail_transaction_using_secured_YES = getActivity().findViewById(avail_transaction_int);

        int instruction_dp_to_recive_every_cradit_int = instruction_dp_to_recive_every_cradit.getCheckedRadioButtonId();
        instruction_dp_to_recive_every_cradit_yes = getActivity().findViewById(instruction_dp_to_recive_every_cradit_int);

        int instruction_dp_to_accept_all_pledge_int = instruction_dp_to_accept_all_pledge.getCheckedRadioButtonId();
        instruction_dp_to_accept_all_pledge_YES = getActivity().findViewById(instruction_dp_to_accept_all_pledge_int);

        int bank_account_as_given_below_through_ECS_int = bank_account_as_given_below_through_ECS.getCheckedRadioButtonId();
        bank_account_as_given_below_through_ECS_YES = getActivity().findViewById(bank_account_as_given_below_through_ECS_int);

        int basic_services_demat_account_facility_int = basic_services_demat_account_facility.getCheckedRadioButtonId();
        basic_services_demat_account_facility_YES = getActivity().findViewById(basic_services_demat_account_facility_int);

        int address_of_nominee_int = address_of_nominee.getCheckedRadioButtonId();
        address_of_nominee_same_as_application = getActivity().findViewById(address_of_nominee_int);

        //durgesh


        int address_of_nominee_int_new = address_of_nominee_new.getCheckedRadioButtonId();
        address_of_nominee_same_as_application_new = getActivity().findViewById(address_of_nominee_int_new);


        int nomi_int = nominee_identification_option.getCheckedRadioButtonId();
        nominee_identification_option_photograph = getActivity().findViewById(nomi_int);


        int nomi_int_new = nominee_identification_option_new.getCheckedRadioButtonId();
        nominee_identification_option_photograph_new = getActivity().findViewById(nomi_int_new);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        try {
            AdditionalObj.put("AdditionalDetailsId", "0");
            AdditionalObj.put("ekycApplicationId", AppInfo.ekycApplicationId);
            AdditionalObj.put("FATCA_IsUsPerson", (is_a_us_person_YES.isChecked()) ? "Yes" : "No");
            AdditionalObj.put("FATCA_TaxResidence", specify_country_residence_INDIA.getText());
            AdditionalObj.put("FATCA_CountryOfCitizenship", specify_country_citizenhip_INDIA.getText());
            AdditionalObj.put("PAST_ActionsTaken", past_actions_YES.getText());

            if(!past_actions_YES.getText().equals("No")) {

                AdditionalObj.put("PAST_ActionsTakenValue", past_action_specification_edt.getText().toString());

            }else{

                AdditionalObj.put("PAST_ActionsTakenValue", "");
            }

            AdditionalObj.put("SB_IsAnotherAuthorisedDealing", sub_broker_YES.getText());

            if (!sub_broker_YES.getText().equals("No")) {

                AdditionalObj.put("SB_NameOfStockBroker", sub_broker_name_edt.getText().toString());
                AdditionalObj.put("SB_NameOfAuthorisedPersonSubBroker", sub_broker_authorised_person_edt.getText().toString());
                AdditionalObj.put("SB_NameOfExchange", sub_broker_exchange_name_edt.getText().toString());
                AdditionalObj.put("SB_ClientCode", sub_broker_client_code_edt.getText().toString());
                AdditionalObj.put("SB_DetailsOfDisputes", sub_broker_dues_edt.getText().toString());
                AdditionalObj.put("SB_IsMemberOrSubBrokerOrAPOfAnyExchange", sebi_registration_group_YES.getText());

                if (!sebi_registration_group_YES.getText().equals("No")) {

                    AdditionalObj.put("SB_SEBIRegistrationNumber", sebi_registration_group_Number_edt.getText().toString());

                }else{

                    AdditionalObj.put("SB_SEBIRegistrationNumber", "");
                }
            } else {

                AdditionalObj.put("SB_NameOfStockBroker", "");
                AdditionalObj.put("SB_NameOfAuthorisedPersonSubBroker", "");
                AdditionalObj.put("SB_NameOfExchange", "");
                AdditionalObj.put("SB_ClientCode", "");
                AdditionalObj.put("SB_DetailsOfDisputes", "");
                AdditionalObj.put("SB_IsMemberOrSubBrokerOrAPOfAnyExchange", "");

            }

            AdditionalObj.put("SI_ContractNoteHoldingTransactionStatement", review_contract_electronically.getText());
            AdditionalObj.put("SI_ReceiveDeliveryInstructionSlip", review_delivery_YES.getText());
            AdditionalObj.put("SI_ShareEmailIdWithRegistrar", shareEmail_YES.getText());
            AdditionalObj.put("SI_ReceiveAnnualReport", recieve_annual_report_electronically.getText());
            AdditionalObj.put("SI_ReceiveDPAccountsStatement", receive_DP_accounts_as_per_sebi.getText());
            AdditionalObj.put("SI_DeclareMobileNumber", declare_mobile_number_belong_to_self.getText());
            AdditionalObj.put("SI_DeclareEmailAddress", declare_mobile_email_belongs_to_self.getText());

            AdditionalObj.put("SI_TRUSTSMSAlertFacility", avail_transaction_using_secured_YES.getText());


            if (BuildConfig.FLAVOR.equalsIgnoreCase("Swastika")) {

                AdditionalObj.put("Credit", instruction_dp_to_recive_every_cradit_yes.getText());
                AdditionalObj.put("PledgeInstructions", instruction_dp_to_accept_all_pledge_YES.getText());
                AdditionalObj.put("InterestThroughECS", bank_account_as_given_below_through_ECS_YES.getText());
                AdditionalObj.put("DematAccFacility", basic_services_demat_account_facility_YES.getText());

            }else if (BuildConfig.FLAVOR.equalsIgnoreCase("Tradingo")) {

            }

            //AdditionalObj.put("IsNominee", make_nomination_YES.getText());

            AdditionalObj.put("IsNominee", nominee_yes_or_no);

            if (radio_cdsl.getText().equals("Only Trading")) {
                AdditionalObj.put("AcOpenLocation", "1");

            }else if (radio_cdsl.getText().equals("Trading + NSDL")) {
                AdditionalObj.put("AcOpenLocation", "NSDL");

            }else{
                AdditionalObj.put("AcOpenLocation", "CDSL");
            }

            //if (!make_nomination_YES.getText().equals("No")) {


            if(!nominee_yes_or_no.equals("No")) {

                  /*JSONArray nomineeArray = new JSONArray();
                  int i=0;
                  while(i<2){
                    i++;
                    JSONObject nomineObj = new JSONObject();
                    nomineObj.put("NomineeId", "0");
                    nomineObj.put("ekycApplicationId", AppInfo.ekycApplicationId);
                    nomineObj.put("NomineeName", edt_firstnominee.getText().toString());
                    nomineObj.put("AddressSameAsAccountHolder", address_of_nominee_same_as_application.getText());


                    nomineObj.put("NomineeAddress1", addone.getText().toString());
                    nomineObj.put("NomineeAddress2", addtwo.getText().toString());
                    nomineObj.put("NomineeAddress3", addthree.getText().toString());
                    nomineObj.put("NomineeCity", addcity.getText().toString());
                    nomineObj.put("NomineePinCode", addpincode.getText().toString());

                        nomineObj.put("PanCard", "");
                        nomineObj.put("NomineePhone","");
                        nomineObj.put("RelationshipWithNominee", relationshipapplicant.getText().toString());
                        nomineObj.put("DOB",dobofnominee1.getText().toString());

                    if(toggleValue.equals("yes")) {

                        nomineObj.put("IsMinor", true);

                    }else{

                        nomineObj.put("IsMinor", false);
                     }

                    nomineObj.put("GuardiansName", name_of_guardian_edt.getText().toString());
                    nomineObj.put("GuardiansAddress1", address_of_guardian_edt.getText().toString());
                    nomineObj.put("GuardiansAddress2", address_of_guardian_edt.getText().toString());
                    nomineObj.put("GuardiansAddress3", address_of_guardian_edt.getText().toString());
                    nomineObj.put("GuardiansPhone", "");
                    nomineObj.put("GuardiansDate",dobofminor.getText().toString());
                    nomineObj.put("GuardiansPlace","");
                    nomineObj.put("SubmittedPlace","");
                    nomineeArray.put(nomineObj);
                    AdditionalObj.put("NomineeDetails", nomineeArray);
                   }*/

                JSONObject Nomine1 = new JSONObject();
                try {
                    Nomine1.put("ekycApplicationId", AppInfo.ekycApplicationId);
                    Nomine1.put("NomineeId", "1");
                    Nomine1.put("NomineeName", edt_firstnominee.getText().toString());
                    Nomine1.put("AddressSameAsAccountHolder", "A");
                    Nomine1.put("NomineeAddress1", addone.getText().toString());
                    Nomine1.put("NomineeAddress2", addtwo.getText().toString());
                    Nomine1.put("NomineeAddress3", addthree.getText().toString());
                    Nomine1.put("NomineeCity",  addcity.getText().toString());
                    Nomine1.put("NomineePinCode", addpincode.getText().toString());
                    Nomine1.put("PanCard", "");
                    Nomine1.put("NomineePhone", "");
                    Nomine1.put("RelationshipWithNominee",relationshipapplicant.getText().toString());
                    Nomine1.put("DOB",dobofnominee1.getText().toString());
                    Nomine1.put("IsMinor", false);
                    Nomine1.put("GuardiansName", name_of_guardian_edt.getText().toString());
                    Nomine1.put("GuardiansAddress1", edt_gurdianaddress_one.getText().toString());
                    Nomine1.put("GuardiansAddress2", edt_gurdianaddress_two.getText().toString());
                    Nomine1.put("GuardiansAddress3", edt_gurdianaddress_three.getText().toString());
                    Nomine1.put("GuardiansPhone", "");
                    Nomine1.put("GuardiansDate", "2020-08-03");
                    Nomine1.put("GuardiansPlace", "");
                    Nomine1.put("SubmittedPlace", "");
                    Nomine1.put("Sequence", "1");
                    Nomine1.put("SharePercentage", edtpercentagesharing.getText().toString());

                }catch (JSONException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JSONObject Nomine2 = new JSONObject();
                try {
                    Nomine2.put("ekycApplicationId", AppInfo.ekycApplicationId);
                    Nomine2.put("NomineeId", "2");
                    Nomine2.put("NomineeName", edt_name_of_nominee2.getText().toString());
                    Nomine2.put("AddressSameAsAccountHolder", "A");
                    Nomine2.put("NomineeAddress1", addres_one_of_nominee2.getText().toString());
                    Nomine2.put("NomineeAddress2", "D");
                    Nomine2.put("NomineeAddress3", "D");
                    Nomine2.put("NomineeCity", "3");
                    Nomine2.put("NomineePinCode", "452001");
                    Nomine2.put("PanCard", "");
                    Nomine2.put("NomineePhone", "");
                    Nomine2.put("RelationshipWithNominee", relationshipapplicant2.getText().toString());
                    Nomine2.put("DOB", dobofnominee2.getText().toString());
                    Nomine2.put("IsMinor", false);
                    Nomine2.put("GuardiansName", "Rahul Singh");
                    Nomine2.put("GuardiansAddress1", "");
                    Nomine2.put("GuardiansAddress2", "");
                    Nomine2.put("GuardiansAddress3", "");
                    Nomine2.put("GuardiansPhone", "");
                    Nomine2.put("GuardiansDate", "2020-08-03");
                    Nomine2.put("GuardiansPlace", "");
                    Nomine2.put("SubmittedPlace", "");
                    Nomine2.put("Sequence", "2");
                    Nomine2.put("SharePercentage",edtpercentagesharing2.getText().toString());

                }catch (JSONException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JSONObject Nomine3 = new JSONObject();
                try {
                    Nomine3.put("ekycApplicationId", AppInfo.ekycApplicationId);
                    Nomine3.put("NomineeId", "3");
                    Nomine3.put("NomineeName", "D3");
                    Nomine3.put("AddressSameAsAccountHolder", "A");
                    Nomine3.put("NomineeAddress1", "D");
                    Nomine3.put("NomineeAddress2", "D");
                    Nomine3.put("NomineeAddress3", "D");
                    Nomine3.put("NomineeCity", "3");
                    Nomine3.put("NomineePinCode", "452001");
                    Nomine3.put("PanCard", "");
                    Nomine3.put("NomineePhone", "");
                    Nomine3.put("RelationshipWithNominee", "Son");
                    Nomine3.put("DOB", "2020-06-05");
                    Nomine3.put("IsMinor", false);
                    Nomine3.put("GuardiansName", "gaurav Singh");
                    Nomine3.put("GuardiansAddress1", "");
                    Nomine3.put("GuardiansAddress2", "");
                    Nomine3.put("GuardiansAddress3", "");
                    Nomine3.put("GuardiansPhone", "");
                    Nomine3.put("GuardiansDate", "2020-08-03");
                    Nomine3.put("GuardiansPlace", "");
                    Nomine3.put("SubmittedPlace", "");
                    Nomine3.put("Sequence", "1");
                    Nomine3.put("SharePercentage", "40");

                }catch (JSONException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(Nomine1);
                jsonArray.put(Nomine2);
                jsonArray.put(Nomine3);
                AdditionalObj.put("NomineeDetails", jsonArray);

                //AdditionalObj;
            }else{

            }
            AdditionalObj.put("Medium","Mobile");
            AdditionalObj.put("CreatedBy", AppInfo.RM_ID);
            AdditionalObj.put("CreadedDate", formattedDate);
            System.out.println("AdditionalObj" + AdditionalObj.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(bottomSheetBehaviorAdditionDoc.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorAdditionDoc.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        if(bottomSheetBehaviorAddNominee.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorAddNominee.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        if(bottomSheetBehaviorAddNominees.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviorAddNominees.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public void Resume() {
        super.onResume();
        closeViewBrockrageDoc();
        closeAdditionalDoc();
        closeAddNominees();
    }

    public void checkeSignStatus() {

        parameters = new HashMap<String, Object>();
        // parameters.put("CompanyId", "1");
        if (BuildConfig.FLAVOR.equalsIgnoreCase("Swastika")) {
            parameters.put("CompanyId", "1");
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("Tradingo")) {
            parameters.put("CompanyId", "2");
        }
        getRes = new getResponse(getActivity());


        getRes.getResponseFromURL(WebServices.getBaseURL +
                        WebServices.GetSignSourceDetails,
                "GetSignSourceDetails",
                parameters, "POST");

    }

    private SSLSocketFactory getSwastikaSSLSocketFactory()
            throws javax.security.cert.CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, java.security.cert.CertificateException, NoSuchProviderException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // InputStream caInput = getActivity().getResources().openRawResource(R.raw.swastika); // this cert file stored in \app\src\main\res\raw folder path
        InputStream caInput = getActivity().getResources().openRawResource(R.raw.swastika); // this cert file stored in \app\src\main\res\raw folder path

        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                        if (chain != null && chain.length > 0) {
                            chain[0].checkValidity();
                        } else {
                            originalTrustManager.checkClientTrusted(chain, authType);
                        }
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                        if (chain != null && chain.length > 0) {
                            chain[0].checkValidity();
                        } else {
                            originalTrustManager.checkServerTrusted(chain, authType);
                        }
                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }


                }
        };
    }

    public void downloadFile(String fileUrl, File directory) {
        try {

            URL url = new URL(fileUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(getSwastikaSSLSocketFactory());

            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
//            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processToEsign() {
        // If the status is true then
        // your will move to the esign process
        Log.d("eSignnnnn", eSign + "");
        Log.d("stateIdddd", stateId + "");
        System.out.println("currentStatu_click" + ">>>>" + currentStatu);

        if (AppInfo.eMudhraSignature) {

            System.out.println("statusIdEMudra" + stateId.toString());
            System.out.println("statusIdEMudra" + eSign + "");
            if (eSign) {
                Intent intent = new Intent(getActivity(), SaveClientCode.class);
                startActivity(intent);
            } else if (stateId.equals("4") && eSign) {
                Intent intent = new Intent(getActivity(), SaveClientCode.class);
                startActivity(intent);
            } else {
                if (eMudhraAccountStatus.equals("Inactive")) {
                    getEmudraAccountStatus();
                } else {
                    acProgressFlower = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(WHITE)
                            .fadeColor(DKGRAY).build();
                    acProgressFlower.show();
                    fillEquityFormForEmudra();
                }

//                Intent intent = new Intent(getActivity(), eSignMudraActivity.class);
//                startActivity(intent);
            }

        } else {

            if (stateId.equals("8") && eSign) {
                Intent intent = new Intent(getActivity(), SaveClientCode.class);
                startActivity(intent);
            } else if (stateId.equals("8") && eSign == false) {
                openAaadharQuestionDialog(getActivity());

            } else if (stateId.equals("2") && eSign) {
                Intent intent = new Intent(getActivity(), SaveClientCode.class);
                startActivity(intent);
            } else if (stateId.equals("2") && eSign == false) {
                openAaadharQuestionDialog(getActivity());
            } else if (currentStatu.equals("ReadyToDispatch")) {
                IsBoolean = true;
                review_applicationMainLayout.setVisibility(View.GONE);
                error_with_aadhar.setVisibility(View.GONE);
                congratulationsLayout.setVisibility(View.GONE);
                aadhar_not_linked.setVisibility(View.VISIBLE);
                //additional_doc.setVisibility(View.GONE);
                reviewWebView.setVisibility(View.GONE);
            } else {
                if (AdditionalObj.length() != 0) {
                    //Toast.makeText(getActivity(), "if", Toast.LENGTH_SHORT).show();
                    callAdditionaldata();
                    // startActivity(new Intent(getActivity(),SaveClientCode.class));
                } else {
                    //startActivity(new Intent(getActivity(),SaveClientCode.class));
                    Callsubmitdata();
                }
            }
        }
    }

    public void newProcessToEsignYesOpt() {
        // If the status is true then
        // your will move to the esign process
        System.out.println("ReviewFragment : " + "newProcessToEsignYesOpt : " + "eSignnnnn :" + eSign + "");
        System.out.println("ReviewFragment : " + "newProcessToEsignYesOpt : " + "stateIdddd" + stateId + "");
        System.out.println("ReviewFragment : " + "newProcessToEsignYesOpt : " + "currentStatu_click" + ">>>>" + currentStatu);

        // pending esign or esign true
        if (stateId.equals("8") && eSign) {
            Intent intent = new Intent(getActivity(), SaveClientCode.class);
            startActivity(intent);

        }
        // pending esign status and esign is false
        else if (stateId.equals("8") && eSign == false) {
            // 1 - e-form
            // 0 -  physical
            isAadharLinked = "1";
            setAaadharLinkedStatus(getActivity(), isAadharLinked);
            fillEquityForm();
        }
        //  In processs status and esign true
        else if (stateId.equals("2") && eSign) {
            Intent intent = new Intent(getActivity(), SaveClientCode.class);
            startActivity(intent);
        }
        else if (stateId.equals("2") && eSign == false) {
            isAadharLinked = "1";
            setAaadharLinkedStatus(getActivity(), isAadharLinked);
            fillEquityForm();
        } else if (currentStatu.equals("ReadyToDispatch")) {
            IsBoolean = true;
            review_applicationMainLayout.setVisibility(View.GONE);
            error_with_aadhar.setVisibility(View.GONE);
            congratulationsLayout.setVisibility(View.GONE);
            aadhar_not_linked.setVisibility(View.VISIBLE);
            //additional_doc.setVisibility(View.GONE);
            reviewWebView.setVisibility(View.GONE);
        } else {
            if (AdditionalObj.length() != 0) {
                //Toast.makeText(getActivity(), "if", Toast.LENGTH_SHORT).show();
                callAdditionaldata();
                //startActivity(new Intent(getActivity(),SaveClientCode.class));
            }else{
                //startActivity(new Intent(getActivity(),SaveClientCode.class));
                Callsubmitdata();
            }}}

    public String checkdigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private void genrateOTPForWebhookEmail(String emailEditText, final TextView resendButtonOTP) {
        try {
            Log.d("EmailOTP", emailEditText + "---");
            Log.d("CustomerIdOTP", AppInfo.CUSTOMERID + "---");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CustomerId", AppInfo.CUSTOMERID);
            jsonObject.put("Email", emailEditText);
            AndroidNetworking.post(WebServices.getBaseURL + "/Webhook/Webhook_GenerateOTP")
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    // .addBodyParameter("CustomerId", AppInfo.CUSTOMERID)
                    // .addBodyParameter("Email", emailEditText)
                    .addJSONObjectBody(jsonObject)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            Log.d("GenrateOTPRFES", response.toString() + "---");
                            if (acProgressFlower != null) {
                                if (acProgressFlower.isShowing()) {
                                    acProgressFlower.dismiss();
                                }
                            }
                            try {
                                JSONObject object = response.getJSONObject("data");
                                //  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                if (object.getBoolean("Status")) {

                                    if (resendButtonOTP != null) {

                                        resendButtonOTP.setVisibility(View.VISIBLE);
                                        new CountDownTimer(30000, 1000) {

                                            public void onTick(long millisUntilFinished) {
                                                resendButtonOTP.setText("0:" + checkdigit(time));
                                                resendButtonOTP.setEnabled(false);
                                                time--;
                                            }

                                            public void onFinish() {
                                                resendButtonOTP.setEnabled(true);
                                                resendButtonOTP.setText("Resend OTP");
                                            }

                                        }.start();
                                    }

                                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}

                        @Override
                        public void onError(ANError error) {

                            Log.d("GenrateOTPRError", error.getMessage().toString() + "---");
                            // handle error
                            if (acProgressFlower != null) {
                                if (acProgressFlower.isShowing()) {
                                    acProgressFlower.dismiss();
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadFile_forPhysicalDoc extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            //  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            String extStorageDirectory = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                System.out.println("pdf_download_code : " + "in android 11");
                extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            } else {
                System.out.println("pdf_download_code : " + "in android 10");
                extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            }
            File folder = new File(extStorageDirectory, "EquityForm");
            folder.mkdir();

            pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            Toast.makeText(getActivity(), "Downloaded successfully", Toast.LENGTH_SHORT).show();

            System.out.println("onPostExecute" + ">>>>>  Downloaded successfully");
        }


    }

    private class DownloadFileForEmudra extends AsyncTask<String, Void, Void> {

        Boolean equityFileWillDownload = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... strings) {

            try {


                System.out.println("FileURL" + new URL(strings[0]) + "")
                ;
//                System.out.println("EquityFileSize", "" + getFileSize(new URL(WebServices.getBaseURL + "/fileupload/downloadFile/" + AppInfo.ekycApplicationId + "/Equity_Form")));
//                System.out.println("EquityFile_URLL", "" + strings[0]);

//                equityFileSize = Double.parseDouble("" + getFileSize(new URL(strings[0])) / 1000000);
                String mFileUrl = strings[0];
                if (!mFileUrl.contains("https")) {
                    mFileUrl = mFileUrl.replace("http", "https");
                    Log.d(ReviewFragment.class.getName(), mFileUrl + "");
                }
                if (getFileSize(new URL(mFileUrl)) < 15002353) {
                    equityFileWillDownload = true;

                    String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                    String fileName = strings[1];
                    System.out.println("EquityFile_URLL" + fileName + ">>" + fileUrl);
                    // -> maven.pdf
                    //  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

                    String extStorageDirectory = "";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                        System.out.println("pdf_download_code : " + "in android 11");
                        extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                    } else {
                        System.out.println("pdf_download_code : " + "in android 10");
                        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    }
                    File folder = new File(extStorageDirectory, "EquityForm");
                    folder.mkdir();

                    pdfFile = new File(folder, fileName);

                    try {
                        pdfFile.createNewFile();
                    } catch (IOException e) {
                        System.out.println("IOException" + ">>" + e.getMessage());
                        e.printStackTrace();
                    }
                    downloadFile(fileUrl, pdfFile);


                } else {
                    equityFileWillDownload = false;
//                    System.out.println("FILESIZEEEEEE", ">>" + equityFileWillDownload);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("MalformedURLException" + ">>" + e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (equityFileWillDownload) {


                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }

                eSignOnEmudra();

            } else {
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                        .setCancelable(false)
                        //.setTitle("Equity and Commodity Form")
                        .setMessage("File is too large, Please select less than 10MB file.")
                        .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                builder.show();

                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }}}
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        Boolean equityFileWillDownload = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            acProgressFlower = new ACProgressFlower.Builder(getActivity())
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(WHITE)
                    .fadeColor(DKGRAY).build();
            acProgressFlower.show();

        }

        @Override
        protected Void doInBackground(String... strings) {

            try {


//                System.out.println("FileURL", new URL(strings[0]) + "")
                ;
//                System.out.println("EquityFileSize", "" + getFileSize(new URL(WebServices.getBaseURL + "/fileupload/downloadFile/" + AppInfo.ekycApplicationId + "/Equity_Form")));
//                System.out.println("EquityFile_URLL", "" + strings[0]);

//                equityFileSize = Double.parseDouble("" + getFileSize(new URL(strings[0])) / 1000000);
                String mFileUrl = strings[0];
                if (!mFileUrl.contains("https")) {
                    mFileUrl = mFileUrl.replace("http", "https");
                    Log.d(ReviewFragment.class.getName(), mFileUrl + "");
                }
                if (getFileSize(new URL(mFileUrl)) < 15002353) {
                    equityFileWillDownload = true;

                    String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                    String fileName = strings[1];
                    System.out.println("EquityFile_URLL" + fileName + ">>" + fileUrl);
                    // -> maven.pdf
                    // String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    String extStorageDirectory = "";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                        System.out.println("pdf_download_code : " + "in android 11");
                        extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                    } else {
                        System.out.println("pdf_download_code : " + "in android 10");
                        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    }
                    File folder = new File(extStorageDirectory, "EquityForm");
                    folder.mkdir();

                    pdfFile = new File(folder, fileName);

                    try {
                        pdfFile.createNewFile();
                    } catch (IOException e) {
                        System.out.println("IOException" + ">>" + e.getMessage());
                        e.printStackTrace();
                    }
                    downloadFile(fileUrl, pdfFile);


                } else {
                    equityFileWillDownload = false;
//                    System.out.println("FILESIZEEEEEE", ">>" + equityFileWillDownload);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("MalformedURLException" + ">>" + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (acProgressFlower != null) {
                if (acProgressFlower.isShowing()) {
                    acProgressFlower.dismiss();
                }
            }

            if (equityFileWillDownload) {


                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }

                eSignDigio();

            } else {
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                        .setCancelable(false)
//                        .setTitle("Equity and Commodity Form")
                        .setMessage("File is too large, Please select less than 10MB file.")
                        .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                builder.show();

                if (acProgressFlower != null) {
                    if (acProgressFlower.isShowing()) {
                        acProgressFlower.dismiss();
                    }
                }

            }

        }
    }


    private class DownloadTask {
        public DownloadTask(FragmentActivity activity, String equity_url) {
        }
    }


    // ESIGN PROCESS

    public void eSignOnEmudra() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                //  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                String extStorageDirectory = "";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    System.out.println("pdf_download_code : " + "in android 11");
                    extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                } else {
                    System.out.println("pdf_download_code : " + "in android 10");
                    extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                }
                File folder = new File(extStorageDirectory, "EquityForm");


                pdfFile = new File(folder, filledFormFileName);
                System.out.println("AMAN_PDF" + pdfFile.toString());

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.pdf_dialog);


                PDFView pdfView = dialog.findViewById(R.id.pdfView);
                pdf_terms_check = dialog.findViewById(R.id.pdf_terms_check);

                Log.d("pdfForEsign", pdfFile + "--");

                if (pdfFile != null) {
                    AppInfo.SIGNEDPDF = pdfFile;
                    pdfView.fromFile(pdfFile)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .enableAnnotationRendering(true)
                            .defaultPage(0)
                            .load();
                }


                TextView menu1 = dialog.findViewById(R.id.menu1);
                TextView menu2 = dialog.findViewById(R.id.menu2);

                menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        if (File_download_dialog != null) {
                            if (File_download_dialog.isShowing())
                                File_download_dialog.dismiss();
                        }

                    }
                });

                menu2.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {


                        if (pdf_terms_check.isChecked()) {

                            dialog.dismiss();



                            Intent i = new Intent(getActivity(), EmudraActivity.class);
                            // i.putExtra("encryptFile", base64_data);
                            i.putExtra("EkycId", AppInfo.ekycApplicationId);
                            i.putExtra("customerName", newCustomerFullName);
                            startActivity(i);

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Please check the checkbox")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }
                });
                dialog.show();
            }
        }, 1000);
    }
    private byte[] loadFile(File pdf_path_str) throws IOException {
        Log.d("loadFile", "called");
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(pdf_path_str));
        byte[] bytes = new byte[(int) pdf_path_str.length()];
        reader.read(bytes, 0, (int) pdf_path_str.length());
        reader.close();
        return bytes;
    }

    public void getEmudraAccountStatus() {
        acProgressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(WHITE)
                .fadeColor(DKGRAY).build();
        acProgressFlower.show();
        String URL = WebServices.getBaseURL + "/emSigner/GetStatusWhenPendingAccount";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("EkycId", AppInfo.ekycApplicationId);
            jsonObject.put("ReferenceNumber", eMudhraReferenceNumber);
            AndroidNetworking.post(URL)
                    .setTag("Check in active account for emudra")
                    .setPriority(Priority.HIGH)
                    .setOkHttpClient(UtilsClass.getSlrClubClient(getActivity()))
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getJSONObject("data").getBoolean("ReqStatus")) {
                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    Intent intent = new Intent(getActivity(), SaveClientCode.class);
                                    startActivity(intent);

                                } else {

                                    if (acProgressFlower != null) {
                                        if (acProgressFlower.isShowing()) {
                                            acProgressFlower.dismiss();
                                        }
                                    }
                                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                                            .setCancelable(false)
                                            .setTitle("Message")
                                            //.setMessage("For eSign App will download form it will take some time.")
                                            .setMessage(response.getJSONObject("data").getString("ReqMessage") + "")
                                            .addButton("Okay", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();

                                                }
                                            });
                                    builder.show();
                                }

                            } catch (JSONException ex) {

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            if (acProgressFlower != null) {
                                if (acProgressFlower.isShowing()) {
                                    acProgressFlower.dismiss();
                                }
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getLoanDetails(String eKycId) {

        acProgressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(WHITE)
                .fadeColor(DKGRAY).build();
        acProgressFlower.show();
        Log.d("myId", AppInfo.ekycApplicationId + "---9999999999");
        getRes.getResponse(WebServices.getBaseURL + WebServices.GetLoanDetailsByEkycId + "?EkycId=" + eKycId,
                "GetLoanDetails",
                null);
    }

    void getLoanDetailsButtonvalidation() {

        if (getLoan_d_bottom.getVisibility() == View.VISIBLE) {
            submit_btn_additional_detail.setVisibility(View.GONE);
        } else {
            submit_btn_additional_detail.setVisibility(View.VISIBLE);
        }
        if (
//                isLoanAmountFilled
//                        && isLoanTenureFilled
//                        &&
                isChequeFirstFilled
                        && isBankFirstFiiled
                        && isBranchFirstFilled
                        && isChequeSecondFilled
                        && isBankSecondfilled
                        && isBranchSecondfilled
                        && isChequeOneUploaded
                        && isChequeTwoUploaded
                        && isbankStatementUploaded
        ) {

            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
            submit_btn_getLoan_detail.setBackgroundResource(R.mipmap.bottombtn);
            submit_btn_getLoan_detail.setEnabled(true);


        } else {
            submit_btn_getLoan_detail.setBackgroundColor(Color.parseColor("#00000000"));
            submit_btn_getLoan_detail.setBackgroundResource(R.drawable.gray_btn);
            submit_btn_getLoan_detail.setEnabled(false);
        }
    }

}
