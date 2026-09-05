# LENS CRM System

A comprehensive Customer Relationship Management system built with Spring Boot (backend) and React (frontend).

## Project Structure

```
crm/
├── lens-svc/          # Backend - Spring Boot Application
├── lens-ui/           # Frontend - React Application
├── start.bat          # Windows startup script
├── start.sh           # Linux/Mac startup script
└── README.md          # This file
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Node.js 18+** and npm
- **SQL Server** (for production) or **H2** (for local development)

## Quick Start

### Windows

From **PowerShell** or **Command Prompt** in the project folder:

```powershell
.\start.bat
```

PowerShell does not run scripts from the current folder unless you prefix with `.\` (see [Command Precedence](https://learn.microsoft.com/powershell/module/microsoft.powershell.core/about/about_command_precedence)).

### Linux/Mac
```bash
chmod +x start.sh
./start.sh
```

This will:
1. Build and start the backend on port 8080
2. Install dependencies and start the frontend on port 3000
3. Open the application in your browser

## Manual Start

### Backend
```bash
cd lens-svc
mvn clean install
mvn spring-boot:run
```

### Frontend
```bash
cd lens-ui
npm install
npm start
```

## Access Points

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **API Docs**: http://localhost:8080/v3/api-docs

## Environment Configuration

### Backend Environment Variables

Create a `.env` file in `lens-svc/` or set system environment variables:

```bash
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DB_URL=jdbc:sqlserver://your-db-host:1433;databaseName=lensdb;encrypt=true;trustServerCertificate=true;
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000
FILE_UPLOAD_DIR=./uploads
SERVER_PORT=8080
```

### Frontend Environment Variables

Create `.env` files in `lens-ui/`:

**.env.development** (local):
```
REACT_APP_BASE_URL=http://localhost:8080
```

**.env.production** (production):
```
REACT_APP_BASE_URL=https://your-production-api.com
```

## Local Development with H2 Database

For local development without an external database, the application can use H2 in-memory database.

1. Set the Spring profile to `dev`:
```bash
cd lens-svc
mvn spring-boot:run -Dspring.profiles.active=dev
```

2. The H2 console will be available at: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:lensdb`
   - Username: `sa`
   - Password: (empty)

## Seed Data

The application includes a seed data script that populates the database with sample records for testing.

To load seed data:
```bash
cd lens-svc
# The data.sql file is automatically loaded on startup in dev profile
mvn spring-boot:run -Dspring.profiles.active=dev
```

Seed data includes:
- 10 Users with different roles
- 10 Customers
- 10 Sales Inquiries
- 10 Pump Seals
- 10 Rotary Joints
- 10 API Plans
- 10 Agitator Seals
- 10 Order Forwarding Memos
- 10 Quotations
- Sample Branches, Departments, Designations

## API Endpoints

### Authentication
- `POST /auth/authenticate` - Login
- `POST /auth/resetPassword` - Reset password

### Customer
- `POST /lens/customer/save` - Create customer
- `PUT /lens/customer/Update` - Update customer
- `GET /lens/customer/get` - Get customer by reference number
- `DELETE /lens/customer/delete` - Delete customer
- `GET /lens/customer/getAll` - Get all customers
- `GET /lens/customer/getAllCustomerByFilter` - Filter customers

### Sales Inquiry
- `POST /lens/salesInquiry/save` - Create sales inquiry
- `PUT /lens/salesInquiry/Update` - Update sales inquiry
- `GET /lens/salesInquiry/get` - Get sales inquiry
- `DELETE /lens/salesInquiry/delete` - Delete sales inquiry
- `GET /lens/salesInquiry/getAll` - Get all sales inquiries
- `GET /lens/salesInquiry/getAllSalesInquiryByFilter` - Filter sales inquiries

### Pump Seal
- `POST /lens/pumpSeal/save` - Create pump seal
- `PUT /lens/pumpSeal/Update` - Update pump seal
- `GET /lens/pumpSeal/get` - Get pump seal
- `DELETE /lens/pumpSeal/delete` - Delete pump seal
- `GET /lens/pumpSeal/getAll` - Get all pump seals
- `GET /lens/pumpSeal/getAllPumpSealByFilter` - Filter pump seals

### Rotary Joint
- `POST /lens/rotaryJoint/save` - Create rotary joint
- `PUT /lens/rotaryJoint/Update` - Update rotary joint
- `GET /lens/rotaryJoint/get` - Get rotary joint
- `DELETE /lens/rotaryJoint/delete` - Delete rotary joint
- `GET /lens/rotaryJoint/getAll` - Get all rotary joints
- `GET /lens/rotaryJoint/getAllRotaryJointByFilter` - Filter rotary joints

### API Plan
- `POST /lens/apiPlan/save` - Create API plan
- `PUT /lens/apiPlan/Update` - Update API plan
- `GET /lens/apiPlan/get` - Get API plan
- `DELETE /lens/apiPlan/delete` - Delete API plan
- `GET /lens/apiPlan/getAll` - Get all API plans
- `GET /lens/apiPlan/getAllApiPlanByFilter` - Filter API plans

### Agitator Seal
- `POST /lens/agitatorSeal/save` - Create agitator seal
- `PUT /lens/agitatorSeal/Update` - Update agitator seal
- `GET /lens/agitatorSeal/get` - Get agitator seal
- `DELETE /lens/agitatorSeal/delete` - Delete agitator seal
- `GET /lens/agitatorSeal/getAll` - Get all agitator seals
- `GET /lens/agitatorSeal/getAllAgitatorSealByFilter` - Filter agitator seals

### Order Forwarding Memo (OFM)
- `POST /lens/ofm/save` - Create OFM
- `PUT /lens/ofm/Update` - Update OFM
- `GET /lens/ofm/get` - Get OFM
- `DELETE /lens/ofm/delete` - Delete OFM
- `GET /lens/ofm/getAll` - Get all OFMs
- `GET /lens/ofm/getAllOfmByFilter` - Filter OFMs

### Quotation
- `POST /lens/quotation/save` - Create quotation
- `PUT /lens/quotation/Update` - Update quotation
- `GET /lens/quotation/get` - Get quotation
- `DELETE /lens/quotation/delete` - Delete quotation
- `GET /lens/quotation/getAll` - Get all quotations

### User Management
- `POST /user/create` - Create user
- `PUT /user/update` - Update user
- `GET /user/get` - Get user by employee ID
- `DELETE /user/delete` - Delete user
- `GET /user/getAll` - Get all users
- `POST /user/grantAdmin` - Grant admin access
- `POST /user/revokeAdmin` - Revoke admin access

## Building for Production

### Backend
```bash
cd lens-svc
mvn clean package
# The WAR file will be in target/lens-svc.war
```

### Frontend
```bash
cd lens-ui
npm run build
# The build output will be in the build/ directory
```

## Troubleshooting

### Backend fails to start
- Check if port 8080 is available
- Verify database connection settings
- Check Maven dependencies: `mvn clean install`

### Frontend fails to start
- Check if port 3000 is available
- Clear node_modules and reinstall: `rm -rf node_modules && npm install`
- Check if REACT_APP_BASE_URL is set correctly

### Database connection issues
- Verify database credentials in environment variables
- Check if database server is accessible
- For H2, ensure dev profile is active

## Technologies

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security
- JWT Authentication
- SQL Server / H2 Database
- Swagger/OpenAPI
- Lombok
- Apache POI

### Frontend
- React 18
- Material-UI (MUI)
- Axios
- React Router DOM
- Formik & Yup
- js-cookie
- jwt-decode

## License

Proprietary - All rights reserved
