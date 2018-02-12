package org.tis.tools.shiro.authenticationToken;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class UserIdPasswordIdentityToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
     /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    /**
     * 用户名
     */
    private String userId;

    /**
     * The password, in char[] format 密码
     */
    private char[] password;

    /**
     * 是否记住我
     * Whether or not 'rememberMe' should be enabled for the corresponding login attempt;
     * default is <code>false</code>
     */
    private boolean rememberMe = false;

    /**
     * 主机名称或ip
     * The location from where the login attempt occurs, or <code>null</code> if not known or explicitly
     * omitted.
     */
    private String host;

    /**
     * 登录身份
     * 用户在应用系统中可能有多个身份，用于在登录时选定
     */
    private String identity;

    /**
     * 应用代码
     * 用户所登录的应用
     */
    private String appCode;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /**
     * JavaBeans 无参构造器.
     */
    public UserIdPasswordIdentityToken() {
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and a
     * <tt>rememberMe</tt> default of <tt>false</tt>.
     *
     * @param userId 用户名
     * @param password 密码
     * @param identity 身份
     * @param appCode 应用代码
     */
    public UserIdPasswordIdentityToken(final String userId, final char[] password, final String identity, final String appCode) {
        this(userId, password, identity, appCode, false, null);
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default of <tt>false</tt>
     * <p/>
     * <p>This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param userId 用户名
     * @param password 密码
     * @param identity 身份
     * @param appCode 应用代码
     */
    public UserIdPasswordIdentityToken(final String userId, final String password, final String identity, String appCode) {
        this(userId, password != null ? password.toCharArray() : null, identity, appCode, false, null);
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted, the
     * inetAddress from where the attempt is occurring, and a default <tt>rememberMe</tt> value of <tt>false</tt>
     *
     * @param userId the userId submitted for authentication
     * @param password the password string submitted for authentication
     * @param host     the host name or IP string from where the attempt is occurring
     * @param appCode 应用代码
     * @since 0.2
     */
    public UserIdPasswordIdentityToken(final String userId,
                                       final char[] password,
                                       final String identity,
                                       final String appCode,
                                       final String host) {
        this(userId, password, identity, appCode, false, host);
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted, the
     * inetAddress from where the attempt is occurring, and a default <tt>rememberMe</tt> value of <tt>false</tt>
     * <p/>
     * <p>This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param userId the userId submitted for authentication
     * @param password the password string submitted for authentication
     * @param host     the host name or IP string from where the attempt is occurring
     * @param appCode 应用代码
     * @since 1.0
     */
    public UserIdPasswordIdentityToken(final String userId,
                                       final String password,
                                       final String identity,
                                       final String appCode,
                                       final String host) {
        this(userId, password != null ? password.toCharArray() : null, identity, appCode, false, host);
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted, as well as if the user
     * wishes their identity to be remembered across sessions.
     *
     * @param userId   the userId submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param appCode 应用代码
     * @since 0.9
     */
    public UserIdPasswordIdentityToken(final String userId,
                                       final char[] password,
                                       final String identity,
                                       final String appCode,
                                       final boolean rememberMe) {
        this(userId, password, identity, appCode, rememberMe, null);
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted, as well as if the user
     * wishes their identity to be remembered across sessions.
     * <p/>
     * <p>This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param userId   the userId submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param appCode 应用代码
     * @since 0.9
     */
    public UserIdPasswordIdentityToken(final String userId,
                                       final String password,
                                       final String identity,
                                       final String appCode,
                                       final boolean rememberMe) {
        this(userId, password != null ? password.toCharArray() : null, identity, appCode, rememberMe, null);
    }

    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     *
     * @param userId   用户名用于认证
     * @param password  密码用于认证
     * @param identity  身份用于认证
     * @param appCode 应用代码
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public UserIdPasswordIdentityToken(final String userId,
                                       final char[] password,
                                       final String identity,
                                       final String appCode,
                                       final boolean rememberMe,
                                       final String host) {

        this.userId = userId;
        this.password = password;
        this.identity = identity;
        this.appCode = appCode;
        this.rememberMe = rememberMe;
        this.host = host;
    }


    /**
     * Constructs a new UserIdPasswordIdentityToken encapsulating the userId and password submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is occurring.
     * <p/>
     * <p>This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param userId   the userId submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public UserIdPasswordIdentityToken(final String userId,
                                       final String password,
                                       final String identity,
                                       final String appCode,
                                       final boolean rememberMe,
                                       final String host) {
        this(userId, password != null ? password.toCharArray() : null, identity, appCode, rememberMe, host);
    }

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    /**
     * Returns the userId submitted during an authentication attempt.
     *
     * @return the userId submitted during an authentication attempt.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId for submission during an authentication attempt.
     *
     * @param userId the userId to be used for submission during an authentication attempt.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * Returns the password submitted during an authentication attempt as a character array.
     *
     * @return the password submitted during an authentication attempt as a character array.
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Sets the password for submission during an authentication attempt.
     *
     * @param password the password to be used for submission during an authentication attempt.
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * Simply returns {@link #getUserId() getuserId()}.
     *
     * @return the {@link #getUserId() userId}.
     * @see org.apache.shiro.authc.AuthenticationToken#getPrincipal()
     */
    public Object getPrincipal() {
//        Map<String, String> map = new HashMap<>();
//        map.put("userId", );
//        map.put("appCode", getAppCode());
        return getUserId();
    }

    /**
     * Returns the {@link #getPassword() password} char array.
     *
     * @return the {@link #getPassword() password} char array.
     * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
     */
    public Object getCredentials() {
        return getPassword();
    }

    /**
     * Returns the host name or IP string from where the authentication attempt occurs.  May be <tt>null</tt> if the
     * host name/IP is unknown or explicitly omitted.  It is up to the Authenticator implementation processing this
     * token if an authentication attempt without a host is valid or not.
     * <p/>
     * <p>(Shiro's default Authenticator allows <tt>null</tt> hosts to support localhost and proxy server environments).</p>
     *
     * @return the host from where the authentication attempt occurs, or <tt>null</tt> if it is unknown or
     *         explicitly omitted.
     * @since 1.0
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host name or IP string from where the authentication attempt occurs.  It is up to the Authenticator
     * implementation processing this token if an authentication attempt without a host is valid or not.
     * <p/>
     * <p>(Shiro's default Authenticator
     * allows <tt>null</tt> hosts to allow localhost and proxy server environments).</p>
     *
     * @param host the host name or IP string from where the attempt is occurring
     * @since 1.0
     */
    public void setHost(String host) {
        this.host = host;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * Returns <tt>true</tt> if the submitting user wishes their identity (principal(s)) to be remembered
     * across sessions, <tt>false</tt> otherwise.  Unless overridden, this value is <tt>false</tt> by default.
     *
     * @return <tt>true</tt> if the submitting user wishes their identity (principal(s)) to be remembered
     *         across sessions, <tt>false</tt> otherwise (<tt>false</tt> by default).
     * @since 0.9
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * Sets if the submitting user wishes their identity (principal(s)) to be remembered across sessions.  Unless
     * overridden, the default value is <tt>false</tt>, indicating <em>not</em> to be remembered across sessions.
     *
     * @param rememberMe value indicating if the user wishes their identity (principal(s)) to be remembered across
     *                   sessions.
     * @since 0.9
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * 清除数据
     * 密码如果不为空，设置成0x00
     * Clears out (nulls) the userId, password, rememberMe, and inetAddress.  The password bytes are explicitly set to
     * <tt>0x00</tt> before nulling to eliminate the possibility of memory access at a later time.
     */
    public void clear() {
        this.userId = null;
        this.host = null;
        this.rememberMe = false;

        if (this.password != null) {
            for (int i = 0; i < password.length; i++) {
                this.password[i] = 0x00;
            }
            this.password = null;
        }

    }

    /**
     * Returns the String representation.  It does not include the password in the resulting
     * string for security reasons to prevent accidentally printing out a password
     * that might be widely viewable).
     *
     * @return the String representation of the <tt>UserIdPasswordIdentityToken</tt>, omitting
     *         the password.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(userId);
        sb.append(", appCode=");
        sb.append(appCode);
        sb.append(", rememberMe=").append(rememberMe);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }

}
