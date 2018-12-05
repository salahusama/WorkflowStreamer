package com.workflowstreamer.core;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Immutable implementation of {@link LoginData}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableLoginData.builder()}.
 */
@SuppressWarnings("all")
@ParametersAreNonnullByDefault
@Generated({"Immutables.generator", "LoginData"})
@Immutable
public final class ImmutableLoginData implements LoginData {
  private final String username;
  private final String password;

  private ImmutableLoginData(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * @return The value of the {@code username} attribute
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * @return The value of the {@code password} attribute
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link LoginData#getUsername() username} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for username
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableLoginData withUsername(String value) {
    if (this.username == value) return this;
    String newValue = Preconditions.checkNotNull(value, "username");
    return new ImmutableLoginData(newValue, this.password);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link LoginData#getPassword() password} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for password
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableLoginData withPassword(String value) {
    if (this.password == value) return this;
    String newValue = Preconditions.checkNotNull(value, "password");
    return new ImmutableLoginData(this.username, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableLoginData} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableLoginData
        && equalTo((ImmutableLoginData) another);
  }

  private boolean equalTo(ImmutableLoginData another) {
    return username.equals(another.username)
        && password.equals(another.password);
  }

  /**
   * Computes a hash code from attributes: {@code username}, {@code password}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + username.hashCode();
    h = h * 17 + password.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code LoginData...} with all non-generated
   * and non-auxiliary attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("LoginData")
        .add("username", username)
        .add("password", password)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link LoginData} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable LoginData instance
   */
  public static ImmutableLoginData copyOf(LoginData instance) {
    if (instance instanceof ImmutableLoginData) {
      return (ImmutableLoginData) instance;
    }
    return ImmutableLoginData.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link com.workflowstreamer.core.ImmutableLoginData ImmutableLoginData}.
   * @return A new ImmutableLoginData builder
   */
  public static ImmutableLoginData.Builder builder() {
    return new ImmutableLoginData.Builder();
  }

  /**
   * Builds instances of type {@link com.workflowstreamer.core.ImmutableLoginData ImmutableLoginData}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_USERNAME = 0x1L;
    private static final long INIT_BIT_PASSWORD = 0x2L;
    private long initBits = 0x3;

    private @Nullable String username;
    private @Nullable String password;

    private Builder() {}

    /**
     * Fill a builder with attribute values from the provided {@link LoginData} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(LoginData instance) {
      Preconditions.checkNotNull(instance, "instance");
      username(instance.getUsername());
      password(instance.getPassword());
      return this;
    }

    /**
     * Initializes the value for the {@link LoginData#getUsername() username} attribute.
     * @param username The value for username 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder username(String username) {
      this.username = Preconditions.checkNotNull(username, "username");
      initBits &= ~INIT_BIT_USERNAME;
      return this;
    }

    /**
     * Initializes the value for the {@link LoginData#getPassword() password} attribute.
     * @param password The value for password 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder password(String password) {
      this.password = Preconditions.checkNotNull(password, "password");
      initBits &= ~INIT_BIT_PASSWORD;
      return this;
    }
    /**
     * Builds a new {@link com.workflowstreamer.core.ImmutableLoginData ImmutableLoginData}.
     * @return An immutable instance of LoginData
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableLoginData build()
        throws IllegalStateException {
      checkRequiredAttributes(); return new ImmutableLoginData(username, password);
    }

    private boolean usernameIsSet() {
      return (initBits & INIT_BIT_USERNAME) == 0;
    }

    private boolean passwordIsSet() {
      return (initBits & INIT_BIT_PASSWORD) == 0;
    }

    private void checkRequiredAttributes() throws IllegalStateException {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
    }
    private String formatRequiredAttributesMessage() {
      List<String> attributes = Lists.newArrayList();
      if (!usernameIsSet()) attributes.add("username");
      if (!passwordIsSet()) attributes.add("password");
      return "Cannot build LoginData, some of required attributes are not set " + attributes;
    }
  }
}
