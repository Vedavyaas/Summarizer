# Deployment Guide - Summarizer Backend

This guide explains how to deploy your Summarizer Backend application to Render.

## 🚀 Quick Deploy to Render

### 1. Push to GitHub
```bash
git add .
git commit -m "Fix account creation errors and prepare for Render deployment"
git push origin main
```

### 2. Connect to Render
1. Go to [Render Dashboard](https://dashboard.render.com)
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Render will auto-detect the `render.yaml` configuration

### 3. Configure Environment Variables
Set these **secrets** in Render dashboard:

| Variable | Description | Required |
|----------|-------------|----------|
| `TWILIO_ACCOUNT_SID` | Your Twilio Account SID | Yes |
| `TWILIO_AUTH_TOKEN` | Your Twilio Auth Token | Yes |
| `TWILIO_PHONE_NUMBER` | Your Twilio WhatsApp number | Yes |
| `ADMIN_USERNAME` | Admin username for basic auth | Yes |
| `ADMIN_PASSWORD` | Admin password for basic auth | Yes |

### 4. Deploy
- Render will automatically build using: `mvn clean package -DskipTests`
- Start command: `java -jar target/Summarizer-*.jar`
- Health check: `/` endpoint

## 🔧 What Was Fixed

### Account Creation Errors
- ✅ Fixed ResetController method conflicts
- ✅ Added proper error handling
- ✅ Fixed repository method calls
- ✅ Added CORS support for frontend

### Production Configuration
- ✅ Environment variable support
- ✅ Render-specific settings
- ✅ Security configurations
- ✅ Database optimizations

## 📋 Pre-deployment Checklist

- [ ] Application compiles: `mvn clean compile`
- [ ] Package builds: `mvn clean package -DskipTests`
- [ ] All environment variables set in Render
- [ ] GitHub repository is up to date
- [ ] render.yaml is in root directory

## 🚨 Common Issues & Solutions

### Build Failures
- **Maven not found**: Ensure `mvn` is available in Render environment
- **Dependency issues**: Check pom.xml has all required dependencies
- **Java version**: Ensure Java 17+ is used

### Runtime Errors
- **Port binding**: Check `PORT` environment variable
- **Database connection**: Verify H2 database path is writable
- **Missing secrets**: Ensure all required environment variables are set

### Account Management Issues
- **OTP not working**: Check Twilio credentials
- **User not found**: Verify database initialization
- **Password reset fails**: Check OTP verification logic

## 🔍 Monitoring & Debugging

### Health Checks
- Main endpoint: `/` (returns index page)
- API status: Check Render logs for startup messages

### Logs
- View logs in Render dashboard
- Check for Spring Boot startup messages
- Monitor for any error exceptions

### Database
- H2 database file: `./data/summarizer.mv.db`
- Database console: Disabled in production for security

## 🌐 Post-Deployment

### Test Endpoints
1. **Home page**: `https://your-app.onrender.com/`
2. **Registration**: `https://your-app.onrender.com/register`
3. **Reset account**: `https://your-app.onrender.com/reset-page`

### Verify Functionality
- [ ] User registration works
- [ ] OTP generation via WhatsApp
- [ ] Password reset functionality
- [ ] Account deletion
- [ ] CORS allows frontend requests

## 📞 Support

If you encounter issues:
1. Check Render build logs
2. Verify environment variables
3. Test locally first: `mvn spring-boot:run`
4. Check application startup logs

---

**🎯 Your application is now ready for production deployment on Render!**
