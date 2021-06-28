package com.application.icafapi.common.constant;

import software.amazon.awssdk.regions.Region;

public class AWS {

    public static final Region REGION = Region.US_EAST_1;
    public static final String PAPER_BUCKET = "icaf-2021-papers";
    public static final String PROPOSAL_BUCKET = "icaf-2021-proposalss";
    public static final String TEMPLATE_BUCKET = "icaf-2021-templates";
    public static final String IMAGE_BUCKET = "icaf-2021-image-bucket";

    private AWS() {}
}
