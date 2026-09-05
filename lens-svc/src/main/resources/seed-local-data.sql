-- Sample business data for the `local` profile (idempotent).
-- Runs after seed-local.sql via spring.sql.init.data-locations.

-- ---------- Customers ----------
INSERT INTO Customer (CustomerName, VendorCode, Branch, CustomerReferenceNumber, CreatedByUser, UpdatedByUser, CreatedOn, UpdatedOn)
SELECT 'ABC Industries', 'VND-1001', 'Baroda', 'CUST/TEST/0001', 'seed', 'seed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM Customer WHERE CustomerReferenceNumber = 'CUST/TEST/0001');

INSERT INTO Customer (CustomerName, VendorCode, Branch, CustomerReferenceNumber, CreatedByUser, UpdatedByUser, CreatedOn, UpdatedOn)
SELECT 'XYZ Process Plants', 'VND-1002', 'Chennai', 'CUST/TEST/0002', 'seed', 'seed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM Customer WHERE CustomerReferenceNumber = 'CUST/TEST/0002');

INSERT INTO ContactDetail (CustomerId, ContactDetailReferenceNo, ContactPerson, EmailId, MobileNumber, CustomerAddress, CreatedByUser, CreatedOn)
SELECT c.CustomerId, 'CONT/TEST/0001', 'Ramesh Patel', 'ramesh@abcind.example', '9876500001',
       'Plot 12, GIDC Estate, Ankleshwar, Gujarat 393002', 'seed', CURRENT_TIMESTAMP
FROM Customer c WHERE c.CustomerReferenceNumber = 'CUST/TEST/0001'
  AND NOT EXISTS (SELECT 1 FROM ContactDetail WHERE ContactDetailReferenceNo = 'CONT/TEST/0001');

INSERT INTO ContactDetail (CustomerId, ContactDetailReferenceNo, ContactPerson, EmailId, MobileNumber, CustomerAddress, CreatedByUser, CreatedOn)
SELECT c.CustomerId, 'CONT/TEST/0002', 'Suresh Iyer', 'suresh@xyzproc.example', '9876500002',
       '45 Industrial Layout, Peenya, Bangalore 560058', 'seed', CURRENT_TIMESTAMP
FROM Customer c WHERE c.CustomerReferenceNumber = 'CUST/TEST/0001'
  AND NOT EXISTS (SELECT 1 FROM ContactDetail WHERE ContactDetailReferenceNo = 'CONT/TEST/0002');

-- ---------- Sales Inquiry + pump item ----------
-- bigint PKs don't autoincrement under SQLite -> supply explicit ids
INSERT INTO SalesInquiry (SalesInquiryId, SalesInquiryReferenceNo, CustomerReferenceNo, CustomerName, CustomerAddress,
  ContactPerson, MobileNumber, Branch, Industry, SourceOfInquiry, CreatedByUser, UpdatedByUser, CreatedOn, UpdatedOn)
SELECT (SELECT COALESCE(MAX(SalesInquiryId),0)+1 FROM SalesInquiry), 'SAL/TEST/0001', 'CUST/TEST/0001', 'ABC Industries',
       'Plot 12, GIDC Estate, Ankleshwar, Gujarat 393002',
       'Ramesh Patel', '9876500001', 'Baroda', 'Chemical', 'Email', 'seed', 'seed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM SalesInquiry WHERE SalesInquiryReferenceNo = 'SAL/TEST/0001');

INSERT INTO PumpInquiry (SalesInquiryId, PumpInquiryReferenceNo, BoilingPoint, FreezingPoint, PercentageOfSolid,
  SolidSize, SpGravity, Speed, Viscosity, Make, Model, PumpMOC, TagNumber,
  Arrangement, PumpType, Stage, Series, Performance, SealArrangement,
  ExistingSealMake, ExistingSealSize, ExistingSealMOC, ExistingSealApiPlan, Branch, CreatedByUser, CreatedOn)
SELECT s.SalesInquiryId, 'SAL/TEST/0001/P1', 0, 0, 0, 0, 0, 0, 0, 'KSB', 'CPKN', 'CI', 'P-101',
       'Horizontal', 'Centrifugal', 'Single', 'Series-A', 'Std', 'Single',
       'John Crane', '50mm', 'SS316', 'Plan 11', 'Baroda', 'seed', CURRENT_TIMESTAMP
FROM SalesInquiry s WHERE s.SalesInquiryReferenceNo = 'SAL/TEST/0001'
  AND NOT EXISTS (SELECT 1 FROM PumpInquiry WHERE PumpInquiryReferenceNo = 'SAL/TEST/0001/P1');

-- ---------- Pump Seal DRF ----------
INSERT INTO PumpSeal (PumpSealId, DrfNumber, Branch, SalesInquiryItemReferenceNo, CustomerName, EndUser,
  CostingRequirement, SealType, ProposedMechanicalSeal, CreatedByUser, UpdatedByUser, CreatedOn, UpdatedOn)
SELECT (SELECT COALESCE(MAX(PumpSealId),0)+1 FROM PumpSeal), '/PUM/TEST/0001', 'Baroda', 'SAL/TEST/0001/P1', 'ABC Industries', 'ABC Ankleshwar Plant', 0,
       'Single', 'Type 21', 'seed', 'seed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM PumpSeal WHERE DrfNumber = '/PUM/TEST/0001');

-- ---------- Quotation + item ----------
INSERT INTO Quotation (QuotationNo, TransactionType, Category, Company, Branch, Customer, CustomerAddress,
  CustomerEnquiryNo, SalesInquiryNumber, EnquiryNo, QuotationDate, KindAttentionTo, Designation,
  GrandTotal, SGST, CGST, IGST, PAndF, Freight, Discount, BudgetaryOffer, GuaranteeWarranty,
  SignatoryName, InsertedByUserId, LastUpdatedByUserId, InsertedOn, LastUpdatedOn)
SELECT 'Q/TEST/0001', 'Sales', 'Mechanical Seal', 'LeakProof', 'Baroda', 'ABC Industries',
       'Plot 12, GIDC Estate, Ankleshwar, Gujarat 393002',
       'ENQ-100', 'SAL/TEST/0001', 'ENQ-100', CURRENT_TIMESTAMP, 'Ramesh Patel', 'Manager',
       115000, 0, 0, 18000, 0, 0, 0, 0, 0, 'Sales Head', 'seed', 'seed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM Quotation WHERE QuotationNo = 'Q/TEST/0001');

INSERT INTO QuotationItem (QuotationId, ItemName, ItemDescription, ItemCode, DrfNo, Quantity,
  UnitPrice, UOM, Currency, Discount, Tax, TotalPrice)
SELECT q.QuotationId, 'Mechanical Seal Type 21', '50mm single spring seal', 'MS-21-50',
       '/PUM/TEST/0001', 2, 48500, 'Nos', 'INR', 0, 18, 97000
FROM Quotation q WHERE q.QuotationNo = 'Q/TEST/0001'
  AND NOT EXISTS (SELECT 1 FROM QuotationItem i WHERE i.QuotationId = q.QuotationId AND i.ItemCode = 'MS-21-50');

-- ---------- OFM + item + end user ----------
INSERT INTO OrderForwardingMemo (OfmNo, QuotationNo, QutationNumber, Branch, Customer, CustomerAddress,
  KindAttentionTo, OrderType, Category, OfmDate, PoNo, PoDate, Priority, Transport, DeliveryPeriod,
  OfmStatus, Company, Engineer, StatutoryRegulatoryRequirements, ProjectOrder, PenaltyApplicable,
  Insurance, QapRequired, RawMaterialTC, QCReport, TestReport, GuaranteeCertificate, FitmentCertificate,
  ComplianceCertificate, Discount, OtherCharges, GrandTotal, CGST, SGST, IGST, Freight, PAndF,
  InsertedByUserId, LastUpdatedByUserId, InsertedOn, LastUpdatedOn)
SELECT 'OFM/TEST/0001', 'Q/TEST/0001', 'Q/TEST/0001', 'Baroda', 'ABC Industries',
       'Plot 12, GIDC Estate, Ankleshwar, Gujarat 393002',
       'Ramesh Patel', 'Regular', 'Mechanical Seal', CURRENT_TIMESTAMP, 'PO-9001', CURRENT_TIMESTAMP,
       'High', 'Road', '4 weeks', 'Open', 'LeakProof', 'QA Seed',
       1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, '0', 115000, 0, 0, 0, 0, 0, 'seed', 'seed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM OrderForwardingMemo WHERE OfmNo = 'OFM/TEST/0001');

INSERT INTO OfmItem (ofmId, SrNo, Header, Factor, Type, Size, Face, Description, CICode, LpItemCode,
  DrfNo, DrawingNo, Quantity, BookedQuantity, Unit, UnitPrice, UnitLPrice, Discount,
  TotalValue, TotalListValue, GrandTotalListPrice, NaDrgNo)
SELECT o.OfmId, 1, 'MS', 'Pump Seal', 'Single', '50mm', 'SiC/C', 'Mechanical Seal Type 21',
       'CI-01', 'LP-MS-21', '/PUM/TEST/0001', 'DRG-100', 2, 0, 'Nos', 48500, 52000, 0,
       97000, 104000, 104000, 0
FROM OrderForwardingMemo o WHERE o.OfmNo = 'OFM/TEST/0001'
  AND NOT EXISTS (SELECT 1 FROM OfmItem i WHERE i.ofmId = o.OfmId AND i.LpItemCode = 'LP-MS-21');

INSERT INTO EndUserDetail (OfmId, Branch, CustomerName, Place, ContactPersonName, MobileNumber, EmailId, EndUserIndustry, Knots)
SELECT o.OfmId, 'Baroda', 'ABC Industries', 'Ankleshwar', 'Ramesh Patel', '9876500001',
       'ramesh@abcind.example', 'Chemical', 'Y'
FROM OrderForwardingMemo o WHERE o.OfmNo = 'OFM/TEST/0001'
  AND NOT EXISTS (SELECT 1 FROM EndUserDetail e WHERE e.OfmId = o.OfmId);

-- ---------- OFM communication history ----------
INSERT INTO OfmCommunication (OfmNo, OfmDate, PreviousActivity, CurrentActivity, Comments, ActivityBy, ActivityOn)
SELECT 'OFM/TEST/0001', CURRENT_TIMESTAMP, NULL, 'OFM Received', 'OFM registered', 'seed', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM OfmCommunication WHERE OfmNo = 'OFM/TEST/0001' AND CurrentActivity = 'OFM Received');

INSERT INTO OfmCommunication (OfmNo, OfmDate, PreviousActivity, CurrentActivity, Comments, ActivityBy, ActivityOn)
SELECT 'OFM/TEST/0001', CURRENT_TIMESTAMP, 'OFM Received', 'Drawing Sent', 'GA drawing shared with customer', 'seed', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM OfmCommunication WHERE OfmNo = 'OFM/TEST/0001' AND CurrentActivity = 'Drawing Sent');

-- QA user needs a branch for branch-scoped lookups (e.g. salesInquiry/get)
INSERT INTO UserBranch (UserId, BranchId)
SELECT u.UserId, b.BranchId FROM Users u, Branch b
WHERE u.EmpId = 'QA001' AND b.BranchName = 'Baroda'
AND NOT EXISTS (SELECT 1 FROM UserBranch x WHERE x.UserId = u.UserId AND x.BranchId = b.BranchId);
