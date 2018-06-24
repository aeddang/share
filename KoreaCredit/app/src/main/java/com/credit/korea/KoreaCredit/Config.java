package com.credit.korea.KoreaCredit;


public class Config {
	public static final String PREFS_NAME="KOREA_CREDIT";

    public static final String KEY_ID="keyid";
    public static final String KEY_PW="keypw";
    public static final String KEY_SNS_TYPE="keysns";
    public static final String RECIEVE_SMS="recieveSmsKey";
    public static final String SMS_NUMBERS="smsNumbers";

    public static final int PAGE_HOME=101;
    public static final int PAGE_MEMBER=201;
    public static final int PAGE_MYPAGE=401;
    public static final int PAGE_CARD=301;

    public static final int POPUP_LIST_RESULT=1002;
    public static final int POPUP_WEB=1001;


    public static final int POPUP_MODIFY=5001;


    public static final int POPUP_PAGE_MYSEARCH=4001;
    public static final int POPUP_CONSUME_DEFAULT=4002;
    public static final int POPUP_CONSUME_MODIFY=4003;
    public static final int POPUP_CONSUME_MODIFY_DETAIL=4004;

    public static final int POPUP_CARD_ADD=3001;
    public static final int POPUP_CARD_BOOK_DESC=3002;
    public static final int POPUP_CARD_BOOK_ADDED=3003;
    public static final int POPUP_CARD_LIST=2003;
    public static final int POPUP_CARD_DETAIL=2004;
    public static final int POPUP_CARD_COMPARE=2005;
    public static final int POPUP_CARD_HIT=2006;
    public static final int POPUP_CARD_FIND=2007;


    public static final String API_PATH="http://www.koreacreditcard.com/";


    public static final String API_LOAD_SMS_NUMBER=API_PATH+"app/card_telnum.asp";
    public static final String API_SEND_SMS=API_PATH+"app/sms_send.asp";
    public static final String API_SEND_SMS_DATAS=API_PATH+"app/sms_send_multi.asp";


    public static final String API_LOGIN=API_PATH+"app/login.asp";
    public static final String API_FIND_IDPW=API_PATH+"app/findid.asp";
    public static final String API_MODIFY_MEMBER=API_PATH+"app/member_edit.asp";
    public static final String API_EXIT_MEMBER=API_PATH+"app/member_out.asp";


    public static final String API_JOIN=API_PATH+"app/member_join.asp";
    public static final String API_CHECK_ID=API_PATH+"app/id_dup.asp";

    public static final String API_SEND_PW=API_PATH+"app/send_key.asp";
    public static final String API_SEND_PW_CHECK=API_PATH+"app/send_key_check.asp";






    public static final String API_LOAD_SIDO=API_PATH+"app/sido.asp";
    public static final String API_LOAD_GUGUN=API_PATH+"app/gugun.asp";

    public static final String API_LOAD_MYINFO=API_PATH+"app/member_info.asp";
    public static final String API_LOAD_MYSEARCH_OPTION=API_PATH+"app/getsearchoption.asp";
    public static final String API_MODIFY_MYSEARCH_OPTION=API_PATH+"app/searchoptioninfo_ok.asp";

    public static final String API_LOAD_MYCARD=API_PATH+"app/mycard_list.asp";


    public static final String API_LOAD_MYCOUNSEL=API_PATH+"app/cardcust_list.asp";

    public static final String API_REQUEST_CARD=API_PATH+"app/cardhave.asp";
    public static final String API_DELETE_CARD=API_PATH+"app/mycard_exec.asp";

    public static final String API_LOAD_DETAIL_CARD=API_PATH+"app/card_view.asp";

    public static final String API_LOAD_COMPANY= API_PATH+"app/svr_check.asp";
    public static final String API_LOAD_BENEFIT_GROUP= API_PATH+"app/group_check.asp";
    public static final String API_LOAD_BENEFIT_GROUP_SUB= API_PATH+"app/sub_check.asp";
    public static final String API_LOAD_COMPANY_SORT= API_PATH+"app/sv_check.asp";

    public static final String API_LOAD_CONSUME_DEFAULT= API_PATH+"app/consumebasicinfo.asp";
    public static final String API_MODIFY_CONSUME_DEFAULT= API_PATH+"app/consume_basicexec.asp";
    public static final String API_LOAD_CONSUME_ADDED = API_PATH+"app/consumeadded.asp";
    public static final String API_LOAD_CONSUME_BENIFIT = API_PATH+"app/consumebenifit.asp";

    public static final String API_LOAD_AUTOUPDATE = API_PATH+"app/get_patupdate_info.asp";
    public static final String API_MODIFY_AUTOUPDATE = API_PATH+"app/get_patupdate_info_ok.asp";



    public static final String API_MODIFY_CONSUME_BENIFIT = API_PATH+"app/consume_exec.asp";
    public static final String API_MODIFY_CONSUME_ADDED = API_PATH+"app/consume_subexec.asp";

    public static final String API_LOAD_BOOK_CARD=API_PATH+"app/cardbook_list.asp";
    public static final String API_LOAD_BOOK_DETAIL=API_PATH+"app/cardbook_uselist.asp";
    public static final String API_MODIFY_BOOK_DETAIL=API_PATH+"app/bookdata_exec.asp";

    public static final String API_LOAD_FAVORIT_CARD=API_PATH+"app/recom_card.asp";
    public static final String API_LOAD_SEARCH_CARD=API_PATH+"app/searchcard_this.asp";

    public static final String API_LOAD_COMPARE_CARD=API_PATH+"app/searchcard_this_sel.asp";

    public static final String API_LOAD_RECOMMEND_CARD=API_PATH+"app/searchcard_use.asp";
    public static final String API_LOAD_THIS_CARD=API_PATH+"app/searchcard_place.asp";
    public static final String API_LOAD_FIND_CARD=API_PATH+"app/card_search.asp";
    public static final String API_LOAD_NAME_CARD=API_PATH+"app/cardname_search.asp";
    public static final String API_DUMMY_PATH="http://buyimbc24.cafe24.com/sitesurfer/";






    public static final String  WEB_AGREEMENT =  "http://www.koreacreditcard.com/m/yak.asp";
    public static final String  WEB_PRIVACY =  "http://www.koreacreditcard.com/m/privacy.asp";

    public static final String  WEB_EVENT =  "http://www.koreacreditcard.com/m/board_list.asp";
    public static final String  WEB_QNA =  "http://www.koreacreditcard.com/m/board_write.asp";
    public static final String  WEB_GUIDE =  "http://www.koreacreditcard.com/m/guide.asp";



}
