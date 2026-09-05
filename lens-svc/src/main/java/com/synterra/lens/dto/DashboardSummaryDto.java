package com.synterra.lens.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {

	private long customerCount;
	private long salesInquiryCount;
	private long pumpSealCount;
	private long rotaryJointCount;
	private long apiPlanCount;
	private long agitatorSealCount;
	private long quotationCount;
	private long ofmCount;
	private long userCount;

	/** OFM count grouped by ofmStatus */
	private Map<String, Long> ofmCountByStatus;

	/** Count per DRF type for the DRF dashboard */
	private Map<String, Long> drfCountByType;

	/** Latest OFM communication activity entries */
	private List<OfmCommunicationDto> recentActivities;
}
