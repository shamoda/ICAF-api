package com.application.icafapi.common.util.constant;

import software.amazon.awssdk.regions.Region;

public class AWS {

    public static final Region REGION = Region.US_EAST_1;
    public static final String PAPER_BUCKET = "icaf-2021-papers";
    public static final String PRESENTATION_BUCKET = "icaf-2021-presentations";
    public static final String PROPOSAL_BUCKET = "icaf-2021-proposals";
    public static final String TEMPLATE_BUCKET = "icaf-2021-templates";

    private AWS() {}
}
