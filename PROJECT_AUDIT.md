# LENS CRM — Project Audit & Coverage Tracker
**Generated:** 2026-08-31T01:11:08.867913
**Scope:** `lens-svc` (Spring Boot backend) and `lens-ui` (React frontend).  Excludes `node_modules`, `target`, `build`, `uploads`, `.git`, `package-lock.json`, `updated.zip`.
**Purpose:** Track every tracked source/config file, expose stubs and cleanup items, dry-run a real business scenario, and list concrete bridging steps.

## Executive Summary
| Metric | Value |
|---|---|
| Total tracked files | 187 |
| Total source/config lines | 21104 |
| Files marked Complete / Doc-Config | 165 |
| Files with Incomplete/Stub or Needs-Cleanup | 22 |
| File-level coverage score | 88.2% |
| Backend API endpoints found | 74 |
| UI routes found | 39 |
| Files with `console.log` / `debugger;` | 20 |
| Files with TODO / FIXME / NotImplemented | 2 |
| Files with `return null;` | 2 |

## Coverage by Module
| Module | Files | Lines | Complete | Incomplete/Cleanup | % |
|---|---|---|---|---|---|
| Backend-Config | 5 | 280 | 5 | 0 | 100.0% |
| Backend-Controller | 3 | 698 | 3 | 0 | 100.0% |
| Backend-DTO | 22 | 1039 | 22 | 0 | 100.0% |
| Backend-Entity | 24 | 2123 | 24 | 0 | 100.0% |
| Backend-Other | 24 | 798 | 23 | 1 | 95.8% |
| Backend-Repository | 21 | 446 | 21 | 0 | 100.0% |
| Backend-Resources | 4 | 144 | 4 | 0 | 100.0% |
| Backend-Service | 14 | 2332 | 14 | 0 | 100.0% |
| Backend-Test | 1 | 9 | 1 | 0 | 100.0% |
| Frontend-APIs | 12 | 1068 | 3 | 9 | 25.0% |
| Frontend-App | 2 | 65 | 2 | 0 | 100.0% |
| Frontend-Components | 3 | 377 | 2 | 1 | 66.7% |
| Frontend-Context | 2 | 64 | 2 | 0 | 100.0% |
| Frontend-Other | 8 | 396 | 8 | 0 | 100.0% |
| Frontend-Pages | 31 | 9163 | 21 | 10 | 67.7% |
| Frontend-Public | 2 | 70 | 2 | 0 | 100.0% |
| Frontend-Router | 1 | 87 | 1 | 0 | 100.0% |
| Frontend-Styles/Assets | 3 | 996 | 3 | 0 | 100.0% |
| Root/Config | 5 | 949 | 4 | 1 | 80.0% |

## Backend API Inventory
| Controller | HTTP | Path | Handler |
|---|---|---|---|
| AuthController.java | REQUEST | /auth | authenticate |
| AuthController.java | POST | /authenticate | authenticate |
| AuthController.java | POST | /resetPassword |  |
| LensRestController.java | REQUEST | /lens |  |
| LensRestController.java | GET | /customer/keyword | searchCustomers |
| LensRestController.java | POST | /customer/save | saveOrUpdateCustomer |
| LensRestController.java | PUT | /customer/Update | UpdateCustomer |
| LensRestController.java | DELETE | /customer/delete | deleteCustomersById |
| LensRestController.java | GET | /customer/get | getCustomerById |
| LensRestController.java | GET | /customer/getAll | getAllCustomer |
| LensRestController.java | GET | /customer/getAllCustomerByFilter | getAllCustomerByFilter |
| LensRestController.java | POST | /salesInquiry/save | saveOrUpdateSalesInquiry |
| LensRestController.java | PUT | /salesInquiry/Update | updateSaleInquiry |
| LensRestController.java | DELETE | /salesInquiry/delete/{InquiryNumber} | deleteSalesInquiryById |
| LensRestController.java | GET | /salesInquiry/get/{InquiryNumber} | getSalesInquiryById |
| LensRestController.java | GET | /salesInquiry/getAll | getAllSalesInquiry |
| LensRestController.java | GET | /salesInquiry/getAllSalesInquiryByFilter | getAllSalesInquiryByFilter |
| LensRestController.java | POST | /pumpSeal/save | savePumpSeal |
| LensRestController.java | PUT | /pumpSeal/Update | updatePumpSeal |
| LensRestController.java | DELETE | /pumpSeal/delete | deletePumpSealById |
| LensRestController.java | GET | /pumpSeal/get | getPumpSealById |
| LensRestController.java | GET | /pumpSeal/getAll | getAllPumpSeals |
| LensRestController.java | GET | /pumpSeal/getAllPumpSealByFilter | getAllPumpSealByFilter |
| LensRestController.java | GET | /drawingMasterTable | getAllMastertableForDrawing |
| LensRestController.java | GET | /queryDrawingMasterTablebyColumn | getMastertableForDrawingColumnValues |
| LensRestController.java | POST | rotaryJoint/save | saveRotaryJoint |
| LensRestController.java | PUT | /rotaryJoint/update | updateRotaryJoint |
| LensRestController.java | DELETE | /rotaryJoint/delete | deleteRotaryJointByDrfNo |
| LensRestController.java | GET | /rotaryJoint/get | getRotaryJointByDrfNo |
| LensRestController.java | GET | /rotaryJoint/getAll | getAllRotaryJoints |
| LensRestController.java | GET | /rotaryJoint/getAllRotaryJointByFilter | getAllRotaryJointByFilter |
| LensRestController.java | POST | /apiPlan/save | saveApiPlan |
| LensRestController.java | PUT | /apiPlan/update | updateApiPlan |
| LensRestController.java | DELETE | /apiPlan/delete | deleteApiPlanById |
| LensRestController.java | GET | /apiPlan/get | getApiPlanById |
| LensRestController.java | GET | /apiPlan/getAll | getAllApiPlans |
| LensRestController.java | GET | /apiPlan/getAllApiPlanByFilter | getAllApiPlanByFilter |
| LensRestController.java | POST | /agitatorSeal/save | saveAgitatorSeal |
| LensRestController.java | PUT | /agitatorSeal/update | updateAgitatorSeal |
| LensRestController.java | DELETE | /agitatorSeal/delete | deleteAgitatorSealById |
| LensRestController.java | GET | /agitatorSeal/get | getAgitatorSealById |
| LensRestController.java | GET | /agitatorSeal/getAll | getAllAgitatorSeals |
| LensRestController.java | GET | /agitatorSeal/getAllAgitatorSealByFilter | getAllAgitatorSealByFilter |
| LensRestController.java | POST | /OrderForwardingMemo/save | saveOrderForwardingMemo |
| LensRestController.java | PUT | OrderForwardingMemo/update | updateOrderForwardingMemo |
| LensRestController.java | DELETE | OrderForwardingMemo/delete | deleteOrderForwardingMemo |
| LensRestController.java | GET | OrderForwardingMemo/get | getOrderForwardingMemo |
| LensRestController.java | GET | OrderForwardingMemo/getAll | getAllOrderForwardingMemos |
| LensRestController.java | GET | /getAllOrderForwardingMemoByFilter | getAllOrderForwardingMemoByFilter |
| LensRestController.java | POST | /Quotation/save | saveQuotation |
| LensRestController.java | PUT | /Quotation/update | updateQuotation |
| LensRestController.java | DELETE | /Quotation/delete | deleteQuotation |
| LensRestController.java | GET | /Quotation/get | getQuotation |
| LensRestController.java | GET | /Quotation/getAll | getAllQuotations |
| LensRestController.java | POST | /ofmCommunication/save | createOFMCommunication |
| LensRestController.java | GET | /ofmCommunication/get | getOfmCommunicationById |
| LensRestController.java | GET | /ofmCommunication/getAll | getAllOfmCommunications |
| LensRestController.java | GET | ofmCommunication/downloadFile/{fileName} | downloadFile |
| UserController.java | REQUEST | /user |  |
| UserController.java | POST | /createUser | getUserByEmpId |
| UserController.java | GET | /getUser | getUserByEmpId |
| UserController.java | GET | /getAllUser | getAllUsers |
| UserController.java | DELETE | /deleteUser | deleteUser |
| UserController.java | PUT | /updateUser | updateUser |
| UserController.java | POST | /saveDepartment | saveDepartment |
| UserController.java | POST | /saveBranch | saveBranch |
| UserController.java | GET | /getAllDepartments | getAllDepartments |
| UserController.java | GET | /getAllBranches | getAllBranches |
| UserController.java | GET | /allDesignations | getAllDesignations |
| UserController.java | POST | /userResetPassword | saveDesignation |
| UserController.java | POST | /saveDesignation | saveDesignation |
| UserController.java | POST | /saveRolesWithAuthorities | saveRolesWithAuthorities |
| UserController.java | POST | /setAdmin/{empId} | setAdmin |
| UserController.java | POST | /removeAdmin/{empId} | revokeAdminAccess |

## Frontend Route Inventory
| Route | Page | File |
|---|---|---|
| / | Navigate | lens-ui/src/router/allRoute.js |
| /user | UserDashboard | lens-ui/src/router/allRoute.js |
| /createAgitator | AgitatorSeal | lens-ui/src/router/allRoute.js |
| /editAgitator | EditAgitator | lens-ui/src/router/allRoute.js |
| /createAgitator/:aId | AgitatorSeal | lens-ui/src/router/allRoute.js |
| /agitatorSuccess/:id | AgitatorSuccessPage | lens-ui/src/router/allRoute.js |
| /SalesInquiry | CreateSales | lens-ui/src/router/allRoute.js |
| /createRotary | CreateRotatory | lens-ui/src/router/allRoute.js |
| /createRotary/:rjId | CreateRotatory | lens-ui/src/router/allRoute.js |
| /editApi | EditApi | lens-ui/src/router/allRoute.js |
| /createApi | CreateApi | lens-ui/src/router/allRoute.js |
| /apiSuccess/:id | ApiSuccessPage | lens-ui/src/router/allRoute.js |
| /createApi/:apId | CreateApi | lens-ui/src/router/allRoute.js |
| /rotarySuccess/:id | RotarySuccessPage | lens-ui/src/router/allRoute.js |
| /createPump | CreatePumpSeal | lens-ui/src/router/allRoute.js |
| /createPump/:pId | CreatePumpSeal | lens-ui/src/router/allRoute.js |
| /editPump | EditPump | lens-ui/src/router/allRoute.js |
| /editRotary | EditRotary | lens-ui/src/router/allRoute.js |
| /pumpSealSuccess/:id | PumpSealSuccessPage | lens-ui/src/router/allRoute.js |
| /editSales | EditSales | lens-ui/src/router/allRoute.js |
| /SalesInquiry/:sId | CreateSales | lens-ui/src/router/allRoute.js |
| /Customer/:rId | Customer | lens-ui/src/router/allRoute.js |
| /Customer | Customer | lens-ui/src/router/allRoute.js |
| /editCustomer | EditCustomer | lens-ui/src/router/allRoute.js |
| /registerSuccess/:id | RegistrationSuccessPage | lens-ui/src/router/allRoute.js |
| /salesSuccess/:sId | SalesSuccessPage | lens-ui/src/router/allRoute.js |
| /updateSuccess/:id | UpdateSuccessPage | lens-ui/src/router/allRoute.js |
| /CreateUser | CreateUser | lens-ui/src/router/allRoute.js |
| /CreateUser/:uId | CreateUser | lens-ui/src/router/allRoute.js |
| /reset | ResetPassword | lens-ui/src/router/allRoute.js |
| /login | Login | lens-ui/src/router/allRoute.js |
| /quotation | CreateQuotation | lens-ui/src/router/allRoute.js |
| /createOfm | CreateOfm | lens-ui/src/router/allRoute.js |
| /createOfm/:oId | CreateOfm | lens-ui/src/router/allRoute.js |
| /ofmSuccess | OfmSuccess | lens-ui/src/router/allRoute.js |
| /editOfm | EditOfm | lens-ui/src/router/allRoute.js |
| /quotationSuccess | QuotationSuccess | lens-ui/src/router/allRoute.js |
| /ofmComm | OfmCommunication | lens-ui/src/router/allRoute.js |
| /updatePassword | UpdatePassword | lens-ui/src/router/allRoute.js |

## File Inventory

### Root/Config
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| .gitignore | 62 | 676 | Complete | - |
| PROJECT_AUDIT.md | 503 | 36423 | Incomplete/Stub | FIXME, NotImplemented, TODO, console.log, debugger;, return null x2, unimplemented |
| README.md | 272 | 7255 | Doc/Config | - |
| start.bat | 51 | 1242 | Doc/Config | - |
| start.sh | 61 | 1285 | Doc/Config | - |

### Backend-Other
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/.gitignore | 33 | 428 | Complete | - |
| lens-svc/README.md | 1 | 28 | Doc/Config | - |
| lens-svc/pom.xml | 118 | 3492 | Doc/Config | - |
| lens-svc/src/main/java/com/synterra/lens/LensApplication.java | 16 | 345 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/ServletInitializer.java | 13 | 418 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/exception/ErrorResponse.java | 22 | 441 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/exception/ExceptionResponseHandler.java | 34 | 1428 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/exception/GlobalExceptionHandler.java | 51 | 2218 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/exception/LensServiceException.java | 21 | 461 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/AgitatorSealSequence.java | 32 | 1092 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/ApiPlanSequence.java | 31 | 1061 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/CommonUtils.java | 22 | 606 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/CustomerIdSequence.java | 37 | 1256 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/DateUtil.java | 14 | 494 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/FileDownloadUtil.java | 29 | 972 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/FileUploadUtil.java | 41 | 1215 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/FileUtils.java | 44 | 1395 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/OrderForwadingMemoItemsDrfNo.java | 38 | 1148 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/PumpSealIdSequence.java | 32 | 1071 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/QuotationNumberGenerator.java | 61 | 2062 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/RotaryJointSequence.java | 32 | 1087 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/SalesInquiryIdSequence.java | 31 | 1096 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/utils/UserDetailUtils.java | 21 | 753 | Incomplete/Stub | return null x1 |
| lens-svc/src/main/java/com/synterra/lens/utils/WebConfig.java | 24 | 693 | Complete | - |

### Backend-Config
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/config/ApplicationConfig.java | 74 | 2780 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/config/CustomUserDetails.java | 57 | 1182 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/config/FileStorageConfig.java | 40 | 1139 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/config/JwtAuthFilter.java | 61 | 2596 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/config/SecurityConfiguration.java | 48 | 2149 | Complete | - |

### Backend-Controller
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/controller/AuthController.java | 36 | 1326 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/controller/LensRestController.java | 519 | 23422 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/controller/UserController.java | 143 | 5077 | Complete | - |

### Backend-DTO
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/dto/AgitatorSealDto.java | 64 | 1952 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/ApiPlanDto.java | 147 | 3092 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/AuthorityDto.java | 18 | 340 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/BranchDto.java | 18 | 320 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/CustomerDetailDto.java | 44 | 1214 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/CustomerDto.java | 41 | 1379 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/CustomerFilterDTO.java | 27 | 661 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/DepartmentDto.java | 16 | 308 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/DesignationDto.java | 27 | 568 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/MocDto.java | 29 | 477 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/OfmCommunicationDto.java | 27 | 693 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/OfmFilterResponseDto.java | 31 | 822 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/OfmItemDto.java | 40 | 834 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/OrderForwardingMemoDTO.java | 68 | 2303 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/PumpSealDto.java | 114 | 3373 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/QuotationDTO.java | 65 | 2030 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/QuotationItemDTO.java | 24 | 551 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/RoleDto.java | 21 | 391 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/RotaryJointDto.java | 53 | 1623 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/SalesInquiryDto.java | 62 | 1279 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/SalesItemDto.java | 48 | 978 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/dto/UserDto.java | 55 | 1826 | Complete | - |

### Backend-Entity
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/entity/AgitatorSeal.java | 150 | 3810 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/ApiPlan.java | 222 | 5659 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/AuthenticationRequest.java | 17 | 310 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/AuthenticationResponse.java | 19 | 406 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Authority.java | 32 | 752 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Branch.java | 34 | 824 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Customer.java | 60 | 1821 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/CustomerDetail.java | 97 | 2751 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Department.java | 31 | 746 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Designation.java | 31 | 776 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/MasterTableForDrawing.java | 37 | 849 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Moc.java | 51 | 1153 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/OfmCommunication.java | 55 | 1303 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/OfmItem.java | 75 | 1683 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/OrderForwardingMemo.java | 220 | 5506 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/PumpSeal.java | 302 | 7211 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Quotation.java | 169 | 4269 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/QuotationItem.java | 58 | 1266 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/ResetPasswordRequest.java | 20 | 392 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/Role.java | 48 | 1377 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/RotaryJoint.java | 117 | 2948 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/SalesInquiry.java | 90 | 2293 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/SalesItem.java | 78 | 2142 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/entity/User.java | 110 | 3505 | Complete | - |

### Backend-Repository
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/repository/AgitatorSealRepository.java | 33 | 1557 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/ApiPlanRepository.java | 31 | 1498 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/AuthorityRepository.java | 18 | 401 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/BranchRepository.java | 12 | 310 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/CustomerDetailRepository.java | 10 | 313 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/CustomerRepository.java | 48 | 2097 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/DepartmentRepository.java | 16 | 399 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/DesignationRepository.java | 15 | 347 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/ItemRepository.java | 22 | 602 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/MastertableForDrawingRepository.java | 14 | 595 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/MocRepository.java | 10 | 280 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/OfmCommunicationRepository.java | 16 | 436 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/OrderForwardingMemoRepository.java | 39 | 1892 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/PumpSealRepository.java | 32 | 1516 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/QuotationItemRepository.java | 17 | 560 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/QuotationRepository.java | 17 | 497 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/RoleRepository.java | 11 | 343 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/RotaryJointRepository.java | 31 | 1545 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/SalesInquiryRepository.java | 31 | 1601 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/SalesItemRepository.java | 10 | 298 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/repository/UserRepository.java | 13 | 363 | Complete | - |

### Backend-Service
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/service/AgitatorSealService.java | 166 | 6959 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/ApiPlanService.java | 160 | 6138 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/AuthService.java | 111 | 4485 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/CustomerService.java | 216 | 9249 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/JwtService.java | 86 | 3130 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/MastertableForDrawingService.java | 22 | 680 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/OfmCommunicationService.java | 130 | 5482 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/OrderForwardingMemoService.java | 227 | 10242 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/PumpSealService.java | 168 | 6662 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/QuotationService.java | 190 | 7326 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/RoleService.java | 30 | 869 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/RotaryJointService.java | 172 | 7016 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/SalesInquiryService.java | 244 | 10343 | Complete | - |
| lens-svc/src/main/java/com/synterra/lens/service/UserService.java | 410 | 16078 | Complete | - |

### Backend-Resources
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/main/resources/application-dev.properties | 43 | 1624 | Doc/Config | - |
| lens-svc/src/main/resources/application-prod.properties | 37 | 1449 | Doc/Config | - |
| lens-svc/src/main/resources/application.properties | 27 | 1657 | Doc/Config | - |
| lens-svc/src/main/resources/data.sql | 37 | 1309 | Doc/Config | - |

### Backend-Test
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-svc/src/test/java/com/synterra/lens/LensApplicationTests.java | 9 | 182 | Complete | - |

### Frontend-Other
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/.gitignore | 2 | 19 | Complete | - |
| lens-ui/README.md | 70 | 3429 | Doc/Config | - |
| lens-ui/package.json | 61 | 1591 | Doc/Config | - |
| lens-ui/src/axios/axiosInstance.js | 27 | 677 | Complete | - |
| lens-ui/src/index.js | 18 | 468 | Complete | - |
| lens-ui/src/reportWebVitals.js | 13 | 375 | Complete | - |
| lens-ui/src/setupTests.js | 5 | 246 | Complete | - |
| lens-ui/src/theme.js | 200 | 5382 | Complete | - |

### Frontend-Public
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/public/index.html | 45 | 1865 | Complete | - |
| lens-ui/public/manifest.json | 25 | 517 | Doc/Config | - |

### Frontend-APIs
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/apis/AgitatorApi.js | 124 | 3434 | Needs-Cleanup | console.log |
| lens-ui/src/apis/ApiPlan.js | 119 | 3160 | Needs-Cleanup | console.log |
| lens-ui/src/apis/CustomerApi.js | 86 | 3076 | Complete | - |
| lens-ui/src/apis/LoginApi.js | 49 | 1618 | Complete | - |
| lens-ui/src/apis/OfmApi.js | 130 | 3745 | Needs-Cleanup | console.log |
| lens-ui/src/apis/PumpSealApi.js | 125 | 3960 | Complete | - |
| lens-ui/src/apis/QuotationApi.js | 21 | 593 | Needs-Cleanup | console.log |
| lens-ui/src/apis/ResetPassword.js | 18 | 443 | Needs-Cleanup | console.log |
| lens-ui/src/apis/RotaryApi.js | 133 | 3406 | Needs-Cleanup | console.log |
| lens-ui/src/apis/SalesInquiryApi.js | 107 | 3256 | Needs-Cleanup | console.log |
| lens-ui/src/apis/SignupApi.js | 120 | 3039 | Needs-Cleanup | console.log |
| lens-ui/src/apis/UserDashboardApi.js | 36 | 1040 | Needs-Cleanup | console.log |

### Frontend-Styles/Assets
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/App.css | 523 | 10521 | Complete | - |
| lens-ui/src/index.css | 70 | 1549 | Complete | - |
| lens-ui/src/styles/layout.css | 403 | 8551 | Complete | - |

### Frontend-App
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/App.js | 57 | 2110 | Complete | - |
| lens-ui/src/App.test.js | 8 | 254 | Complete | - |

### Frontend-Components
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/components/global/PageShell.jsx | 25 | 745 | Complete | - |
| lens-ui/src/components/global/SideBar.jsx | 287 | 13272 | Needs-Cleanup | console.log |
| lens-ui/src/components/global/TopBar.jsx | 65 | 2457 | Complete | - |

### Frontend-Context
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/contextApi/AuthContext.js | 55 | 1318 | Complete | - |
| lens-ui/src/contextApi/useToken.js | 9 | 205 | Complete | - |

### Frontend-Pages
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/Pages/Quotation/CreateQuotation.js | 1167 | 33372 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/Quotation/QuotationSuceess.js | 25 | 771 | Complete | - |
| lens-ui/src/Pages/User/CreateUser.js | 423 | 13133 | Incomplete/Stub | TODO |
| lens-ui/src/Pages/User/UserDashboard.js | 141 | 5715 | Complete | - |
| lens-ui/src/Pages/agitator/AgitatorSuccess.js | 27 | 941 | Complete | - |
| lens-ui/src/Pages/agitator/CreateAgitator.js | 406 | 11899 | Complete | - |
| lens-ui/src/Pages/agitator/EditAgitator.js | 209 | 7494 | Complete | - |
| lens-ui/src/Pages/apiPlan/ApiSuccess.js | 28 | 927 | Complete | - |
| lens-ui/src/Pages/apiPlan/CreateApiPlan.js | 519 | 15841 | Complete | - |
| lens-ui/src/Pages/apiPlan/EditApiPlan.js | 218 | 7580 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/customerPage/createCustomer/Customer.js | 293 | 8795 | Complete | - |
| lens-ui/src/Pages/customerPage/createCustomer/CustomerForm.css | 96 | 1684 | Complete | - |
| lens-ui/src/Pages/customerPage/createCustomer/CustomerSuccess.js | 27 | 980 | Complete | - |
| lens-ui/src/Pages/customerPage/editCustomer/EditCustomer.js | 201 | 7329 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/customerPage/editCustomer/UpdateSuccess.js | 28 | 1008 | Complete | - |
| lens-ui/src/Pages/login/Login.js | 121 | 4170 | Complete | - |
| lens-ui/src/Pages/ofm/CreateOfm.js | 1307 | 37148 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/ofm/EditOfm.js | 271 | 8807 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/ofm/OfmSuccess.js | 23 | 750 | Complete | - |
| lens-ui/src/Pages/ofmCommunication/OfmCommunication.js | 307 | 9809 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/pumpSeal/CreatePumpSeal.js | 1381 | 40110 | Complete | - |
| lens-ui/src/Pages/pumpSeal/EditPumpSeal.js | 206 | 7290 | Complete | - |
| lens-ui/src/Pages/pumpSeal/PumpSealSuccess.js | 28 | 933 | Complete | - |
| lens-ui/src/Pages/resetPassword/ResetPassword.js | 136 | 4266 | Complete | - |
| lens-ui/src/Pages/resetPassword/UpdateReset.js | 128 | 3980 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/rotatoryJoint/CreateRotatory.js | 427 | 13165 | Complete | - |
| lens-ui/src/Pages/rotatoryJoint/EditRotryJoint.js | 205 | 7478 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/rotatoryJoint/RotarySuccess.js | 28 | 934 | Complete | - |
| lens-ui/src/Pages/salesinquiryPage/CreateSales.js | 567 | 15409 | Complete | - |
| lens-ui/src/Pages/salesinquiryPage/EditSales.js | 189 | 6909 | Needs-Cleanup | console.log |
| lens-ui/src/Pages/salesinquiryPage/SalesSuccess.js | 31 | 972 | Complete | - |

### Frontend-Router
| File | Lines | Size (B) | Status | Notes/Flags |
|---|---|---|---|---|
| lens-ui/src/router/allRoute.js | 87 | 5096 | Complete | - |

## Red Flag Files
| File | Status | Flags | Lines |
|---|---|---|---|
| lens-svc/src/main/java/com/synterra/lens/utils/UserDetailUtils.java | Incomplete/Stub | return null x1 | 21 |
| lens-ui/src/apis/AgitatorApi.js | Needs-Cleanup | console.log | 124 |
| lens-ui/src/apis/ApiPlan.js | Needs-Cleanup | console.log | 119 |
| lens-ui/src/apis/OfmApi.js | Needs-Cleanup | console.log | 130 |
| lens-ui/src/apis/QuotationApi.js | Needs-Cleanup | console.log | 21 |
| lens-ui/src/apis/ResetPassword.js | Needs-Cleanup | console.log | 18 |
| lens-ui/src/apis/RotaryApi.js | Needs-Cleanup | console.log | 133 |
| lens-ui/src/apis/SalesInquiryApi.js | Needs-Cleanup | console.log | 107 |
| lens-ui/src/apis/SignupApi.js | Needs-Cleanup | console.log | 120 |
| lens-ui/src/apis/UserDashboardApi.js | Needs-Cleanup | console.log | 36 |
| lens-ui/src/components/global/SideBar.jsx | Needs-Cleanup | console.log | 287 |
| lens-ui/src/Pages/apiPlan/EditApiPlan.js | Needs-Cleanup | console.log | 218 |
| lens-ui/src/Pages/customerPage/editCustomer/EditCustomer.js | Needs-Cleanup | console.log | 201 |
| lens-ui/src/Pages/ofm/CreateOfm.js | Needs-Cleanup | console.log | 1307 |
| lens-ui/src/Pages/ofm/EditOfm.js | Needs-Cleanup | console.log | 271 |
| lens-ui/src/Pages/ofmCommunication/OfmCommunication.js | Needs-Cleanup | console.log | 307 |
| lens-ui/src/Pages/Quotation/CreateQuotation.js | Needs-Cleanup | console.log | 1167 |
| lens-ui/src/Pages/resetPassword/UpdateReset.js | Needs-Cleanup | console.log | 128 |
| lens-ui/src/Pages/rotatoryJoint/EditRotryJoint.js | Needs-Cleanup | console.log | 205 |
| lens-ui/src/Pages/salesinquiryPage/EditSales.js | Needs-Cleanup | console.log | 189 |
| lens-ui/src/Pages/User/CreateUser.js | Incomplete/Stub | TODO | 423 |
| PROJECT_AUDIT.md | Incomplete/Stub | FIXME, NotImplemented, TODO, console.log, debugger;, return null x2, unimplemented | 503 |

## Business Dry Run — "Synterra Seals Pvt. Ltd."
### Scenario
A sales engineer logs in, creates a new customer, captures a sales inquiry for a pump-seal replacement, adds technical specifications, raises a quotation, and the order is forwarded via an OFM.
### Step-by-step reality check
| Step | Who | UI Page(s) | Backend API | What can go wrong / what is missing |
|---|---|---|---|---|
| 1. Login | Sales engineer | Login.js, ResetPassword.js | POST /auth/authenticate | No MFA, no brute-force lockout, no password expiry. |
| 2. Create customer | Sales engineer | Customer.js | POST /lens/customer/save | Only basic master data; no GST/address duplicates check, no validation of branch/department linkage. |
| 3. Raise sales inquiry | Sales engineer | CreateSales.js | POST /lens/salesInquiry/save | Missing inquiry status lifecycle (Draft/Submitted/Approved). No email/WhatsApp trigger. |
| 4. Add technical spec | Engineer | CreatePumpSeal.js, CreateRotatory.js, CreateAgitator.js, CreateApiPlan.js | POST /lens/pumpSeal/save, etc. | Forms exist but there is no link between inquiry and technical line items in the schema shown; DTOs and entities may be isolated. |
| 5. Generate quotation | Sales engineer | CreateQuotation.js | POST /lens/quotation/save | No PDF export, no numbering/validity logic, no approval workflow. |
| 6. Forward order (OFM) | Manager | CreateOfm.js, OfmCommunication.js | POST /lens/ofm/save | No status, no digital signature, no dispatch/tracking integration. |
| 7. Admin control | Admin | CreateUser.js, UserDashboard.js | POST /user/create, etc. | No audit log of who granted/revoked admin rights. |
### Key observations from the dry run
- The app is a **data-entry repository**, not a **workflow engine**.
- Master records (Customer, Sales Inquiry, Technical specs, Quotation, OFM) are created independently; there is no enforced relational progression.
- No status transitions, approval gates, notifications, or audit trail surfaced in the files.
- `console.log` and `debugger;` statements in the UI are a risk for production deployments.
- `return null;` in backend code is not always wrong, but in several files it may mask unimplemented branches.

## Gap Analysis & Self-Contained Bridging Steps
These fixes can be done without needing customer data from you:
| Gap | Evidence in audit | How to bridge without external data |
|---|---|---|
| A. No test suite | lens-svc/src/test has only 1 item; no unit tests in lens-ui | Generate small H2-backed JUnit tests for each Controller/Service, and basic React smoke tests with @testing-library/react. |
| B. Un-cleaned debug statements | 20 UI files contain console.log/debugger | Replace console.log with a tiny logger utility or remove; set eslint rule no-console for build. |
| C. No API contract validation | README lists endpoints but no schema for error responses | Add @Valid to DTOs, global @ControllerAdvice, and standard ApiResponse<T> wrapper. |
| D. No workflow state machine | No status fields or transitions in entities/DTOs | Add an `enum Status` to each business entity with a small state-machine helper and persistence. |
| E. No audit trail | No createdBy/createdAt/updatedBy/updatedAt in reviewed schema | Use JPA `@CreatedDate`/`@LastModifiedDate` + `AuditorAware` to stamp every record. |
| F. No RBAC beyond login | Security config exists but no role-based method security | Add `@PreAuthorize` on controllers with the existing Role/Authority entities. |
| G. No PDF/document output | Quotation/OFM forms exist but no export | Integrate Apache POI for Excel or a light PDF library (OpenPDF) for quotations/OFMs. |
| H. No production hardening | Local env vars, seed data, `dev` profile defaults | Add `application-prod.yml`, health endpoint, CORS whitelist, and file-upload size/type limits. |
| I. No frontend route guards | App.js routes may not protect admin pages | Add a `<ProtectedRoute>` wrapper keyed on `jwt-decode` role. |
| J. No feature usage analytics | No logging of page/API usage | Add a lightweight `usePageView` hook and a `@Slf4j` `AuditFilter` for non-personal access logs. |

## 100% Coverage Checklist
Use this section to tick off completion. When every item is addressed, the project can be considered ready.
1. [ ] All TODO / FIXME / NotImplemented markers resolved
2. [ ] All `console.log` / `debugger;` removed or gated by environment
3. [ ] Every backend DTO has validation annotations
4. [ ] Every controller has unit/integration tests
5. [ ] Every React page has a matching route and API integration
6. [ ] All entities have audit fields and status workflow
7. [ ] Auth is enforced per role (admin/sales/manager)
8. [ ] Quotation/OFM PDF or Excel export works
9. [ ] H2 dev seed data and production config are separate
10. [ ] Build passes (`mvn clean install`, `npm run build`)

---
**Next action recommended:** Resolve the red-flag files above, then re-run this generator to update the tracker.
