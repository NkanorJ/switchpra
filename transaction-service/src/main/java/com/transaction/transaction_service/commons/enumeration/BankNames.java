package com.transaction.transaction_service.commons.enumeration;

import lombok.Getter;

@Getter
public enum BankNames {

    ACCESS_BANK("044", "044150149", "000014", "ACCESS BANK PLC"),
    PROVIDUS_BANK("101", "101152019", "000023", "PROVIDUS BANK"),
    KEYSTONE_BANK("082", "082150017", "000002", "KEYSTONE BANK PLC"),
    DIAMOND_BANK("063", "063150162", "000005", "DIAMOND BANK PLC"),
    ECO_BANK("050", "050150311", "000010", "ECOBANK NIGERIA PLC"),
    FIDELTY_BANK("070", "070150003", "000007", "FIDELITY BANK PLC"),
    FIRST_BANK("011", "011152303", "000016", "FIRST BANK OF NIGERIA PLC"),
    FCMB_BANK("214", "214150018", "000003", "FIRST CITY MONUMENT BANK PLC"),
    GT_BANK("058", "058152052", "000013", "GUARANTY TRUST BANK PLC"),
    STANBIC_BANK("221", "221159522", "000012", "STANBIC IBTC BANK PLC"),
    CITI_BANK("023", "023150005", "000009", "CITI BANK"),
    SKYE_BANK("076", "076151006", "000008", "SKYE BANK PLC"),
    SUNTRUST_BANK("100", "100152049", "000022", "SUNTRUST BANK"),
    STAN_CHAN_BANK("068", "068150057", "000021", "STANDARD CHARTERED BANK PLC"),
    STERLING_BANK("232", "232150029", "000001", "STERLING BANK PLCC"),
    UNION_BANK("032", "032156825", "000018", "UNION BANK OF NIGERIA PLC"),
    UBA("033", "033154282", "000004", "UNITED BANK FOR AFRICA PLC"),
    UNITY_BANK("215", "215082334", "000011", "UNITY BANK PLC"),
    WEMA_BANK("035", "035150103", "000017", "WEMA BANK PLC"),
    ZENITH_BANK("057", "057150013", "000015", "ZENITH INTERNATIONAL BANK PLC"),
    HERITAGE_BANK("030", "030159992", "000020", "HERITAGE BANK"),
    JAIZ_BANK("301", "301080020", "000006", "JAIZ BANK"),
    VFD_BANK("090110", "090110", "090110", "VFD BANK"),
    HOPE_PSB("120002", "120002", "120002", "HOPE PSB"),
    TITAN_TRUST_BANK("102", "102150010", "000025", "TITAN TRUST BANK"),
    NINE_MOBILE_PSB("120001", "120001", "120001", "9MOBILE PSB"),
    EKONDO_MFB("090097", "033072830", "090097", "EKONDO MICROFINANCE BANK"),
    PARALLEX_BANK("104", "104150005", "000030", "PARALLEX BANK"),
    NPF_MICROFINANCE_BANK("070001", "070001", "070001", "NPF MICROFINANCE BANK"),
    KUDA_MICROFINANCE_BANK("090267", "090267", "090267", "KUDA MICROFINANCE BANK"),
    MONIEPOINT_MICROFINANCE_BANK("090405", "090405", "090405", "MONIEPOINT MICROFINANCE BANK"),
    PAGA("100002", "100002", "100002", "PAGA"),
    PAYCOM("100004", "100004", "100004", "PAYCOM"),
    AG_MORTGAGE("100028", "100028", "100028", "AG MORTGAGE"),
    PALMPAY("100033", "100033", "100033", "PALMPAY"),
    NIBSS_TEST("998", "999998", "999998", "NIBSS_TEST"),
    GLOBUS("103", "103150000", "000027", "GLOBUS BANK");

    private final String code;
    private final String name;
    private final String sortCode;
    private final String schemeName;


    BankNames(String code, String sortCode, String schemeName, String name) {
        this.code = code;
        this.name = name;
        this.sortCode = sortCode;
        this.schemeName = schemeName;
    }


}
