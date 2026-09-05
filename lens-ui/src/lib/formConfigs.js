// Field configs for the config-driven forms.
// Field: { name (supports 'a.b' paths), label?, type, options?, column?, url?, pick?, required?, readOnly? }
// Types: text (default) | number | textarea | select | master | api | date | datetime | checkbox

const t = (name, o = {}) => ({ name, type: 'text', ...o });
const num = (name, o = {}) => ({ name, type: 'number', ...o });
const sel = (name, options, o = {}) => ({ name, type: 'select', options, ...o });
const mst = (name, column, o = {}) => ({ name, type: 'master', column, ...o });
const api = (name, url, pick, o = {}) => ({ name, type: 'api', url, pick, ...o });
const dt = (name, o = {}) => ({ name, type: 'datetime', ...o });
const area = (name, o = {}) => ({ name, type: 'textarea', ...o });
const chk = (name, o = {}) => ({ name, type: 'checkbox', ...o });

const branchField = api('branch', '/user/getAllBranches', (b) => b.branchName || b.name);
const branchFieldAt = (path) => api(path, '/user/getAllBranches', (b) => b.branchName || b.name);

// ---------- shared DRF blocks ----------

const drfGeneral = (extra = []) => ({
  title: 'General',
  fields: [
    t('drfNumber', { readOnly: true, placeholder: 'Auto-generated' }),
    branchField,
    t('salesInquiryItemReferenceNo', { label: 'Sales Inquiry Item Ref No' }),
    t('customerName'),
    t('endUser'),
    t('costingRequirement'),
    t('createdByUser', { readOnly: true }),
    dt('createdOn', { readOnly: true }),
    t('updatedByUser', { readOnly: true }),
    dt('updatedOn', { readOnly: true }),
    ...extra,
  ],
});

const sealFaces = (prefix) => ({
  title: `${prefix} Seal Faces`,
  fields: [
    t(`${prefix}IBFace`, { label: 'IB Face' }), t(`${prefix}IBElastomer`, { label: 'IB Elastomer' }),
    t(`${prefix}IBSpringElement`, { label: 'IB Spring Element' }),
    t(`${prefix}IBContactHardware`, { label: 'IB Contact Hardware' }),
    t(`${prefix}IBNonContactHardware`, { label: 'IB Non-Contact Hardware' }),
    t(`${prefix}OBFace`, { label: 'OB Face' }), t(`${prefix}OBElastomer`, { label: 'OB Elastomer' }),
    t(`${prefix}OBSpringElement`, { label: 'OB Spring Element' }),
    t(`${prefix}OBContactHardware`, { label: 'OB Contact Hardware' }),
    t(`${prefix}OBNonContactHardware`, { label: 'OB Non-Contact Hardware' }),
  ],
});

const apiPlanSection = {
  title: 'API Plan',
  fields: [
    mst('apiFlushingPlans', 'API_PLAN', { label: 'Flushing Plan' }),
    mst('apiBarrierBufferPlans', 'API_PLAN', { label: 'Barrier/Buffer Plan' }),
    mst('apiAtmosphericPlans', 'API_PLAN', { label: 'Atmospheric Plan' }),
    mst('apiCollectionPlans', 'API_PLAN', { label: 'Collection Plan' }),
  ],
};

const otherSection = {
  title: 'Other Details',
  fields: [area('otherDetailsAccessories', { label: 'Accessories' }), area('otherDetailsRemarks', { label: 'Remarks' })],
};

const file = (name, filetype, o = {}) => ({ name, type: 'file', filetype, ...o });

const attachments = (a1, a2, filetype) => ({
  title: 'Attachments',
  fields: [
    file(a1[0], filetype, { label: a1[1] }),
    file(a2[0], filetype, { label: a2[1] }),
  ],
});

const PUMP_INQUIRY = [
  t('pumpInquiryItem.pumpInquiryReferenceNo', { label: 'Inquiry Ref No' }),
  branchFieldAt('pumpInquiryItem.branch'),
  t('pumpInquiryItem.make'), t('pumpInquiryItem.model'),
  mst('pumpInquiryItem.pumpMOC', 'MOC', { label: 'Pump MOC' }),
  mst('pumpInquiryItem.impellerCasingMOC', 'MOC', { label: 'Impeller/Casing MOC' }),
  mst('pumpInquiryItem.shaftMOC', 'MOC', { label: 'Shaft MOC' }),
  t('pumpInquiryItem.bearingBKT'), t('pumpInquiryItem.tagNumber'),
  t('pumpInquiryItem.arrangement'), mst('pumpInquiryItem.pumpType', 'PUMP_TYPE', { label: 'Pump Type' }),
  t('pumpInquiryItem.stage'), t('pumpInquiryItem.casingType'),
  mst('pumpInquiryItem.series', 'SERIES'), mst('pumpInquiryItem.performance', 'Performance'),
  mst('pumpInquiryItem.sealArrangement', 'SEAL_ARRANGEMENT'),
  mst('pumpInquiryItem.existingSealMake', 'MAKE'), t('pumpInquiryItem.existingSealSize'),
  mst('pumpInquiryItem.existingSealMOC', 'MOC'), mst('pumpInquiryItem.existingSealApiPlan', 'API_PLAN'),
  t('pumpInquiryItem.suctionPressure'),
];

const meas = (names) => ({ title: 'Measurements', fields: names.map((n) => t(n)) });

// ---------- per-form configs ----------

export const FORM_CONFIGS = {
  pumpSeal: {
    title: 'Pump Seal DRF',
    base: '/lens/pumpSeal',
    getParam: 'pumpSealReferenceNo',
    idField: 'drfNumber',
    sections: [
      drfGeneral(),
      { title: 'Pump Data & Existing Seal (from inquiry)', fields: PUMP_INQUIRY },
      {
        title: 'Existing / Proposed Seal',
        fields: [
          t('existingSealGA', { label: 'Existing Seal GA' }), t('existingSealSeries'),
          t('existingSealShaftDia'), t('existingSealSize'), t('existingSealType'),
          t('proposedMechanicalSeal'), mst('sealType', 'SEAL_TYPE'),
          t('newSealShaftDia'), t('newSealBoreDia'), t('newSealBoreDepth'),
          t('newSealNearestObstruction'), mst('newSealType', 'SEAL_TYPE'),
        ],
      },
      sealFaces('existingSeal'), sealFaces('newSeal'),
      apiPlanSection,
      meas(['measurementTypeOfStuffingBox', 'measurementShaftOd', 'measurementStuffingBoxId',
        'measurementStuffingBoxDepth', 'measurementNearestObstruction', 'measurementSpigotDia',
        'measurementSocketDepth', 'measurementShaftSleeveAvailable', 'measurementSleeveOd',
        'measurementStuffingBoxThroatDia', 'measurementSleeveShoulderLength', 'measurementSleeveExtensionLength',
        'measurementShaftHubDistance', 'measurementNumberOfStuds', 'measurementStudSize',
        'measurementBoltCircleDiameter', 'measurementStartAngle', 'measurementFlushSize',
        'measurementFlushAngle', 'measurementQuenchSize', 'measurementQuenchAngle',
        'measurementDrainSize', 'measurementDrainAngle', 'measurementStuffingBox']),
      otherSection,
      attachments(['attachmentReferenceMechanicalSealDrawing', 'Seal Drawing Ref'], ['attachmentStuffingBoxDetails', 'Stuffing Box Details Ref'], 'pumpseal'),
    ],
  },

  agitatorSeal: {
    title: 'Agitator Seal DRF',
    base: '/lens/agitatorSeal',
    getParam: 'pumpSealReferenceNo',
    idField: 'drfNumber',
    sections: [
      drfGeneral(),
      {
        title: 'Agitator Data',
        fields: [
          t('agitatorMake'), t('agitatorModel'), t('agitatorEntry'),
          t('agitatorTagNumber'), mst('agitatorVesselMoc', 'MOC'),
        ],
      },
      {
        title: 'Existing / Proposed Seal',
        fields: [
          t('existingSealGA', { label: 'Existing Seal GA' }), t('existingSealSeries'),
          t('existingSealShaftDia'), t('existingSealSize'), t('existingSealType'),
          t('proposedMechanicalSeal'),
          t('newSealShaftDia'), t('newSealBoreDia'), t('newSealBoreDepth'),
          t('newSealNearestObstruction'), mst('newSealType', 'SEAL_TYPE'),
        ],
      },
      sealFaces('existingSeal'), sealFaces('newSeal'),
      apiPlanSection,
      meas(['measurementTypeOfPadPlate', 'measurementShaftOd', 'measurementPadPlateId',
        'measurementNearestObstruction', 'measurementSpigotDia', 'measurementSocketDepth',
        'measurementShaftDiaD1', 'measurementShaftDiaD2', 'measurementShaftStepDistanceL1',
        'measurementDistanceBetweenStepsL2', 'measurementPadPlateThicknessT', 'measurementRadiusR',
        'glandBoltingNumberOfStuds', 'glandBoltingStudSize', 'glandBoltingBoltCircleDiameter',
        'glandBoltingStartAngle', 'connectionFlushSize', 'connectionFlushAngle',
        'connectionQuenchSize', 'connectionQuenchAngle', 'connectionDrainSize', 'connectionDrainAngle']),
      otherSection,
      attachments(['attachmentReferenceMechanicalSealDrawing', 'Seal Drawing Ref'], ['attachmentPadPlateDetails', 'Pad Plate Details Ref'], 'agitator'),
    ],
  },

  apiPlan: {
    title: 'API Plan DRF',
    base: '/lens/apiPlan',
    getParam: 'apiPlanReferenceNo',
    idField: 'drfNumber',
    sections: [
      drfGeneral(),
      {
        title: 'API Plan Details',
        fields: [
          t('existingSealSupportMake'), t('existingSealSupportApiPlan'),
          t('existingSealSupportCapacity'), t('existingSealSupportReferenceApiPlanDrawingNumber', { label: 'Ref Drawing No' }),
          t('existingSealSupportHeatExchangeType'), t('existingSealSupportHeatExchangeArea'),
          t('existingSealSupportStandard'),
          mst('mocVessel', 'MOC'), mst('mocCoolingCoil', 'MOC'),
          mst('mocPipingAndFitting', 'MOC'), mst('mocBladder', 'MOC'), mst('mocStructuralParts', 'MOC'),
          area('instruments'), area('recommendedBufferOrBarrierFluid'),
          area('accessories'), area('remarks'),
        ],
      },
      {
        title: 'Inquiry Item',
        fields: [
          t('apiPlanInquiryItem.apiPlanInquiryReferenceNo', { label: 'Inquiry Ref No' }),
          t('apiPlanInquiryItem.equipmentMake'), t('apiPlanInquiryItem.equipmentModel'),
          t('apiPlanInquiryItem.equipmentType'), t('apiPlanInquiryItem.arrangement'),
          t('apiPlanInquiryItem.tagNumber'), mst('apiPlanInquiryItem.pumpMOC', 'MOC'),
          t('apiPlanInquiryItem.drawingNumber'), t('apiPlanInquiryItem.mechanicalSealMake'),
          t('apiPlanInquiryItem.mechanicalSealSeries'), t('apiPlanInquiryItem.connectionSize'),
          t('apiPlanInquiryItem.shaftSize'), t('apiPlanInquiryItem.rotation'), t('apiPlanInquiryItem.mawp'),
        ],
      },
      attachments(['attachmentAvl', 'AVL Ref'], ['attachmentSpecification', 'Specification Ref'], 'apiplan'),
      { title: 'Other Attachments', fields: [file('attachmentReferenceMechanicalSealDrawing', 'apiplan', { label: 'Seal Drawing Ref' })] },
    ],
  },

  rotaryJoint: {
    title: 'Rotary Joint DRF',
    base: '/lens/rotaryJoint',
    getParam: 'rotaryJointReferenceNo',
    idField: 'drfNumber',
    sections: [
      drfGeneral(),
      {
        title: 'Equipment & Operating Data',
        fields: [
          t('equipment'), mst('make', 'MAKE'), t('model'), t('fluid'),
          t('operatingTemperature'), mst('operatingTemperatureUnit', 'UNIT'),
          t('flowRate'), t('speed'), t('operatingPressure'), mst('operatingPressureUnit', 'UNIT'),
        ],
      },
      {
        title: 'Existing / Proposed Rotary Joint',
        fields: [
          mst('existingRotaryJointMake', 'MAKE'), t('existingRotaryJointModelType'),
          t('existingRotaryJointConnectionSize'), t('existingRotaryJointConnectionType'),
          t('jointType'), mst('proposedRotaryJointMake', 'MAKE'),
          t('proposedRotaryJointModelType'), t('inletConnectionSize'), t('outletConnectionSize'),
          t('connectionType'), t('handing'), t('inletFlangedSize'), t('outletFlangedSize'),
          file('referenceDrawing', 'rotaryjoint'),
        ],
      },
    ],
  },

  salesInquiry: {
    title: 'Sales Inquiry',
    base: '/lens/salesInquiry',
    getParam: 'itemReferenceNo',
    idField: 'salesInquiryReferenceNo',
    sections: [
      {
        title: 'Inquiry Details',
        fields: [
          t('salesInquiryReferenceNo', { readOnly: true, placeholder: 'Auto-generated' }),
          t('customerReferenceNo'), t('customerName', { required: true }),
          area('customerAddress'), t('contactPerson'), t('mobileNumber'),
          mst('sourceOfInquiry', 'SOURCE_OF_INQUIRY'), mst('industry', 'INDUSTRY'),
          branchField,
        ],
      },
    ],
    items: [
      {
        key: 'pumpInquiries', title: 'Pump Seal Items', addLabel: 'Add Pump Seal',
        columns: [
          t('make'), t('model'), t('pumpMOC'), t('tagNumber'), t('arrangement'),
          t('pumpType'), t('stage'), t('series'), t('performance'), t('sealArrangement'),
          t('existingSealMake'), t('existingSealSize'), t('existingSealMOC'), t('existingSealApiPlan'),
        ],
      },
      {
        key: 'agitatorInquiries', title: 'Agitator Items', addLabel: 'Add Agitator',
        columns: [
          t('series'), t('performance'), t('sealArrangement'), t('sealType'),
          t('existingSealMake'), t('existingSealSize'), t('existingSealMOC'),
          t('directionOfRotation'), t('speed'), t('fluid'), t('nature'),
        ],
      },
      {
        key: 'apiPlanInquiries', title: 'API Plan Items', addLabel: 'Add API Plan',
        columns: [
          t('equipmentMake'), t('equipmentModel'), t('equipmentType'), t('arrangement'),
          t('tagNumber'), t('pumpMOC'), t('mechanicalSealMake'), t('mechanicalSealSeries'),
          t('connectionSize'), t('shaftSize'), t('rotation'), t('mawp'),
        ],
      },
      {
        key: 'rotaryJointInquiries', title: 'Rotary Joint Items', addLabel: 'Add Rotary Joint',
        columns: [
          t('equipment'), t('make'), t('model'), t('fluid'), t('operatingTemperature'),
          t('flowRate'), t('speed'), t('operatingPressure'), t('jointType'),
          t('existingRotaryJointMake'), t('existingRotaryJointModelType'), file('referenceDrawing', 'rotaryjoint'),
        ],
      },
    ],
  },

  quotation: {
    title: 'Quotation',
    base: '/lens/Quotation',
    getParam: 'id',
    idField: 'quotationId',
    sections: [
      {
        title: 'General',
        fields: [
          t('quotationNo', { readOnly: true, placeholder: 'Auto-generated' }),
          t('transactiontype'), mst('category', 'OFM_CATEGORY'),
          dt('quotationDate'), t('country'), t('company'),
          t('customerEnquiryNo'), t('salesInquiryNumber'),
          branchField, t('enquiryNo'), dt('enquiryDate'),
          t('customer', { required: true }), area('customerAddress'),
          t('kindAttentionTo'), t('designation'),
        ],
      },
      {
        title: 'Commercial',
        fields: [
          dt('dueOn'), mst('transport', 'TRANSPORT'), area('specialComments'),
          t('revisionNo'), dt('revisionDate'), num('validityWeeks'),
          mst('quotationSource', 'QUOTATION_SOURCE'), t('deliverySchedule'),
          t('engineer'), chk('budgetaryOffer'), area('paymentTerms'),
          mst('priceTerm', 'PRICE_TERM'),
          num('pAndF', { label: 'P&F %' }), num('freight'), num('discount'),
          num('sgst', { label: 'SGST %' }), num('cgst', { label: 'CGST %' }), num('igst', { label: 'IGST %' }),
          num('grandTotal', { readOnly: true }),
        ],
      },
      {
        title: 'Statements & Sign-off',
        fields: [
          area('startStatement'), area('endStatement'), area('statement'),
          t('name', { label: 'Signatory Name' }), t('signatoryDesignation'),
          chk('guaranteeWarranty', { label: 'Guarantee/Warranty' }),
          t('guarantee'), t('warranty'),
        ],
      },
    ],
    items: [
      {
        key: 'items', title: 'Quotation Items', addLabel: 'Add Item',
        columns: [
          t('itemName'), area('itemDescription'), t('drfNo'), t('itemCode'),
          num('quantity'), num('unitPrice'), mst('uom', 'UNIT', { label: 'UOM' }),
          t('currency'), num('discount'), num('tax'), num('totalPrice'),
        ],
      },
    ],
  },

  ofm: {
    title: 'Order Forwarding Memo',
    base: '/lens/OrderForwardingMemo',
    getParam: 'ofmNo',
    idField: 'ofmId',
    sections: [
      {
        title: 'General',
        fields: [
          t('ofmNo', { readOnly: true, placeholder: 'Auto-generated' }),
          t('qutationNumber', { label: 'Quotation Transfer' }),
          t('quotationNo'), t('poNo'), dt('poDate'),
          mst('orderType', 'ORDER_TYPE', { options: ['ARC', 'Regular', 'Tender'] }),
          mst('category', 'OFM_CATEGORY'),
          t('invoiceTo'), t('transportThrough'), t('customer', { required: true }),
          area('customerAddress'), t('kindAttentionTo'), mst('priority', 'PRIORITY'),
          mst('transport', 'TRANSPORT'), t('deliveryPeriod'),
          dt('ofmDate'), t('preQANo'), dt('preQADate'),
          t('oaNo'), dt('oaDate'), branchField,
        ],
      },
      {
        title: 'Details',
        fields: [
          sel('statutoryRegulatoryRequirements', ['Yes', 'No'], { label: 'Statutory & Regulatory Requirements' }),
          area('specialInformation'),
          t('engineer'), mst('industry', 'INDUSTRY'),
          sel('projectOrder', ['Yes', 'No']),
          sel('penaltyApplicable', ['Yes', 'No']),
          t('poReceived'), t('location'),
          area('paymentTerms'), sel('ofmStatus', ['Open', 'In Progress', 'Sent to Production', 'Dispatched', 'Closed']),
          sel('company', ['LeakProof', 'Test']),
          sel('qapRequired', ['Yes', 'No'], { label: 'QAP Required' }),
        ],
      },
      {
        title: 'Inspection & Certificates',
        fields: [
          chk('externalInspection'), t('externalInspectionWhere'), t('externalInspectionByWhom'),
          chk('rawMaterialTC', { label: 'Raw Material TC' }),
          chk('qcReport', { label: 'QC Report' }),
          chk('testReport', { label: 'Test Report' }),
          chk('guaranteeCertificate', { label: 'Guarantee Certificate' }),
          chk('fitmentCertificate', { label: 'Fitment Certificate' }),
          chk('complianceCertificate', { label: 'Compliance Certificate' }),
        ],
      },
      {
        title: 'Insurance & Charges',
        fields: [
          chk('insurance'),
          sel('insuranceBy', ['Customer', 'Supplier']),
          sel('insuranceBorneBy', ['Customer', 'Supplier']),
          num('otherCharges'), num('discount'),
        ],
      },
      {
        title: 'Consignee & End User',
        fields: [
          t('consigneeName'), area('consigneeAddress'),
          t('endUserDetail.branch'), t('endUserDetail.customerName'),
          t('endUserDetail.place'), t('endUserDetail.contactPersonName'),
          t('endUserDetail.mobileNumber'), t('endUserDetail.emailId'),
          mst('endUserDetail.endUserIndustry', 'INDUSTRY'), t('endUserDetail.knots'),
        ],
      },
    ],
    items: [
      {
        key: 'ofmItems', title: 'OFM Items', addLabel: 'Add Item',
        columns: [
          num('srNo'), t('header'),
          sel('factor', ['Agitator Seal', 'Pump Seal', 'Other Seal']),
          t('type'), t('size'), t('face'),
          area('description'), t('ciCode'), t('lpItemCode'), t('drfNo'), t('drawingNo'),
          num('quantity'), num('bookedQuantity'), mst('unit', 'UNIT'),
          num('unitPrice'), num('unitLPrice'), num('discount'),
          num('totalValue'), num('totalListValue'), num('grandTotalListPrice'),
          chk('naDrgNo', { label: 'NA Drg' }),
        ],
      },
    ],
  },

  customer: {
    title: 'Customer',
    base: '/lens/customer',
    getParam: 'customerRefrenceNumber',
    idField: 'customerId',
    sections: [
      {
        title: 'Customer Details',
        fields: [
          t('customerReferenceNumber', { readOnly: true, placeholder: 'Auto-generated' }),
          branchField, t('customerName', { required: true }), t('vendorCode'),
        ],
      },
    ],
    items: [
      {
        key: 'contactDetail', title: 'Contact Details', addLabel: 'Add Contact',
        columns: [
          t('contactPerson'), t('designation'), t('emailId'),
          t('mobileNumber'), area('address'), t('city'), t('state'), t('pinCode'),
        ],
      },
    ],
  },

  user: {
    title: 'User',
    base: null, // user endpoints live under /user/*
    sections: [
      {
        title: 'User Details',
        fields: [
          t('firstName', { required: true }), t('middleName'), t('lastName'),
          t('empId', { required: true }), t('password', { type: 'text' }),
          api('designation', '/user/allDesignations', (d) => d.designationName || d.name),
          chk('resetPasswordRequired'),
        ],
      },
    ],
  },
};
