package tbc.uncagedmist.tamilnaduservices.Common;

import android.content.Context;
import android.content.Intent;

public class Common {

    //Ration Card URLs
    public static final String REGISTER_RATION = "https://www.tnpds.gov.in/pages/registeracard/register-a-card.xhtml";
    public static final String STATUS_NEW_RATION = "https://www.tnpds.gov.in/pages/registeracard/register-a-card-status.xhtml";
    public static final String DUPLICATE_RATION = "https://www.tnpds.gov.in/login.xhtml";
    public static final String DUPLICATE_RATION_STATUS = "https://www.tnpds.gov.in/login.xhtml";
    public static final String ADD_MEMBER = "https://www.tnpds.gov.in/login.xhtml";
    public static final String CHANGE_ADDRESS = "https://www.tnpds.gov.in/login.xhtml";
    public static final String FAMILY_HEAD_CHANGE = "https://www.tnpds.gov.in/login.xhtml";
    public static final String REMOVE_FAMILY = "https://www.tnpds.gov.in/login.xhtml";
    public static final String CARD_SERVICES = "https://www.tnpds.gov.in/pages/servicerequest/service-request-status-1.xhtml";
    public static final String CONVERT_RATION = "https://www.tnpds.gov.in/login.xhtml";
    public static final String RAISE_COMPLAINT = "https://www.tnpds.gov.in/pages/complaint.xhtml";
    public static final String DEPARTMENT_LOGIN = "https://cas.tnpds.gov.in/login?service=https%3A%2F%2Fg2g.tnpds.gov.in%2F";
    public static final String NFSA_REPORTS = "https://www.tnpds.gov.in/pages/reports/pds-nfsa-report-state.xhtml";
    public static final String PDS_REPORTS = "https://www.tnpds.gov.in/pages/reports/pds-report-state.xhtml";

    //Voter id URLs
    public static final String VOTER_REPRINT = "https://voterportal.eci.gov.in/";
    public static final String TRACK_VOTER = "https://www.nvsp.in/Forms/trackstatus";
    public static final String VOTER_SERVICES = "https://www.nvsp.in/";
    public static final String SEARCH_VOTER = "https://electoralsearch.in/";
    public static final String APPLY_VOTER = "https://www.nvsp.in/Account/Login";
    public static final String DOWNLOAD_VOTER = "https://www.nvsp.in/Home/DownloadPdf";
    public static final String EDIT_VOTER = "https://www.nvsp.in/Account/Login";

    public static final String SEARCH_AWAS = "https://pmaymis.gov.in/Open/Find_Beneficiary_Details.aspx";
    public static final String BENE_AWAS = "https://rhreporting.nic.in/netiay/AdvanceSearch.aspx";
    public static final String LIST_AWAS = "https://rhreporting.nic.in/netiay/SocialAuditReport/BeneficiaryDetailForSocialAuditReport.aspx";

    public static final String WIN_URL = "https://894.win.qureka.com";

    public static final String PRIVACY_URL =
            "https://docs.google.com/document/d/10j_zSXwh2mhKt1ZiXAvPMqc01CDFTRMooAwb4DtgRf0/edit?usp=sharing";

    public static String CurrentURL;

    public static void shareApp(Context context)    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Never Miss A Thing About Ration Card. Install Tamilnadu Ration Card App and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.tamilnaduservices";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(intent, "Share Tamilnadu Ration Card App Using"));
    }
}
