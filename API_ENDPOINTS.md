# API Endpoints - Account Management

This document describes all the account management endpoints that utilize your stored username, password, and Twilio credentials.

## 🔐 **Account Management Endpoints**

### 1. **Check Account Status**
**GET** `/reset/check-account?phoneNumber={phone}`

Check if an account exists and has Twilio credentials configured.

**Response:**
```json
{
  "exists": true,
  "hasTwilioCredentials": true,
  "username": "john_doe",
  "phoneNumber": "+1234567890",
  "success": true
}
```

**Use Case:** Verify account existence before attempting operations.

---

### 2. **Get Account Information**
**GET** `/reset/account-info?phoneNumber={phone}&otp={otp}`

Get detailed account information (requires OTP verification).

**Response:**
```json
{
  "userInfo": {
    "username": "john_doe",
    "phoneNumber": "+1234567890",
    "hasTwilioCredentials": true
  },
  "success": true
}
```

**Use Case:** Display account details in user dashboard.

---

### 3. **Send Reset OTP**
**GET** `/reset/account?phoneNumber={phone}`

Send OTP using the **stored Twilio credentials** from the user's account.

**Response:**
```json
{
  "message": "OTP sent to your WhatsApp number using stored credentials.",
  "success": true,
  "phoneNumber": "+1234567890"
}
```

**Use Case:** Password reset, account deletion, or credential updates.

---

### 4. **Update Twilio Credentials**
**POST** `/reset/update-twilio`

Update Twilio SID and Auth Token for an account (requires OTP verification).

**Parameters:**
- `phoneNumber`: User's phone number
- `otp`: 6-digit OTP code
- `newSid`: New Twilio Account SID
- `newAuth`: New Twilio Auth Token

**Response:**
```json
{
  "message": "Twilio credentials updated successfully.",
  "success": true
}
```

**Use Case:** Allow users to update their Twilio credentials.

---

### 5. **Reset Password**
**POST** `/reset/password`

Reset password using OTP verification.

**Parameters:**
- `phoneNumber`: User's phone number
- `otp`: 6-digit OTP code
- `newPassword`: New password

**Response:**
```json
{
  "message": "Password changed successfully.",
  "success": true,
  "username": "john_doe",
  "phoneNumber": "+1234567890"
}
```

**Use Case:** Password reset after OTP verification.

---

### 6. **Get Username**
**GET** `/reset/username?phoneNumber={phone}&otp={otp}`

Retrieve username using OTP verification.

**Response:**
```json
{
  "username": "john_doe",
  "phoneNumber": "+1234567890",
  "success": true
}
```

**Use Case:** Username recovery.

---

### 7. **Delete Account**
**DELETE** `/reset/account`

Delete account and all associated data (requires OTP verification).

**Parameters:**
- `phoneNumber`: User's phone number
- `otp`: 6-digit OTP code

**Response:**
```json
{
  "message": "Account deleted successfully.",
  "success": true,
  "deletedUsername": "john_doe",
  "deletedPhoneNumber": "+1234567890"
}
```

**Use Case:** Account deletion with confirmation.

---

## 🚀 **How It Works with Your Data Structure**

### **Stored Data Utilization:**
1. **Username & Password**: Used for authentication and account identification
2. **Phone Number**: Primary identifier for all operations
3. **Twilio SID & Auth**: Automatically used for OTP generation
4. **No External Credentials**: All operations use stored user data

### **Security Features:**
- ✅ **OTP Verification**: All sensitive operations require WhatsApp OTP
- ✅ **Data Validation**: Checks for existing accounts and credentials
- ✅ **Error Handling**: Comprehensive error messages without data exposure
- ✅ **Transaction Safety**: Database operations are transactional

---

## 📱 **Frontend Integration Examples**

### **Check Account Before Operations:**
```javascript
// First check if account exists
const checkAccount = async (phoneNumber) => {
  const response = await fetch(`/reset/check-account?phoneNumber=${phoneNumber}`);
  const data = await response.json();
  
  if (data.exists && data.hasTwilioCredentials) {
    // Proceed with OTP request
    requestOTP(phoneNumber);
  } else if (data.exists && !data.hasTwilioCredentials) {
    // Show message to configure Twilio credentials
    showMessage("Please configure Twilio credentials first");
  } else {
    // Account doesn't exist
    showMessage("Account not found");
  }
};
```

### **Password Reset Flow:**
```javascript
const resetPassword = async (phoneNumber, otp, newPassword) => {
  const formData = new FormData();
  formData.append('phoneNumber', phoneNumber);
  formData.append('otp', otp);
  formData.append('newPassword', newPassword);
  
  const response = await fetch('/reset/password', {
    method: 'POST',
    body: formData
  });
  
  const data = await response.json();
  if (data.success) {
    showSuccess(`Password reset for ${data.username}`);
  } else {
    showError(data.error);
  }
};
```

---

## 🔧 **Environment Variables for Render**

Set these in your Render dashboard:

| Variable | Description | Example |
|----------|-------------|---------|
| `TWILIO_ACCOUNT_SID` | Default Twilio SID | `AC1234567890abcdef` |
| `TWILIO_AUTH_TOKEN` | Default Twilio Auth Token | `1234567890abcdef` |
| `TWILIO_PHONE_NUMBER` | Default Twilio WhatsApp number | `+1234567890` |

---

## 🎯 **Key Benefits of This Approach**

1. **Self-Contained**: Each user's account has their own Twilio credentials
2. **Secure**: OTP verification for all sensitive operations
3. **Flexible**: Users can update their own Twilio credentials
4. **Efficient**: No need to store credentials globally
5. **Scalable**: Each account manages its own WhatsApp integration

---

## 🚨 **Error Handling**

All endpoints return consistent error responses:

```json
{
  "error": "Descriptive error message",
  "success": false
}
```

**Common Error Scenarios:**
- **User not found**: Account doesn't exist
- **OTP verification failed**: Invalid or expired OTP
- **Twilio credentials not configured**: Missing SID/Auth
- **Internal server error**: System-level issues

---

**🎉 Your account management system is now fully optimized to use stored credentials!**
