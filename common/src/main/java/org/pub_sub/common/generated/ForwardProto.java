package org.pub_sub.common.generated;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: proto/forward.proto
// Protobuf Java Version: 4.31.1

@com.google.protobuf.Generated
public final class ForwardProto {
  private ForwardProto() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 31,
      /* patch= */ 1,
      /* suffix= */ "",
      ForwardProto.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code SourceType}
   */
  public enum SourceType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>BROKER = 0;</code>
     */
    BROKER(0),
    /**
     * <code>PUBLISHER = 1;</code>
     */
    PUBLISHER(1),
    UNRECOGNIZED(-1),
    ;

    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 31,
        /* patch= */ 1,
        /* suffix= */ "",
        SourceType.class.getName());
    }
    /**
     * <code>BROKER = 0;</code>
     */
    public static final int BROKER_VALUE = 0;
    /**
     * <code>PUBLISHER = 1;</code>
     */
    public static final int PUBLISHER_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static SourceType valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static SourceType forNumber(int value) {
      switch (value) {
        case 0: return BROKER;
        case 1: return PUBLISHER;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<SourceType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        SourceType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<SourceType>() {
            public SourceType findValueByNumber(int number) {
              return SourceType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return ForwardProto.getDescriptor().getEnumTypes().get(0);
    }

    private static final SourceType[] VALUES = values();

    public static SourceType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private SourceType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:SourceType)
  }

  public interface ForwardMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ForwardMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string source = 1;</code>
     * @return The source.
     */
    java.lang.String getSource();
    /**
     * <code>string source = 1;</code>
     * @return The bytes for source.
     */
    com.google.protobuf.ByteString
        getSourceBytes();

    /**
     * <code>.SourceType source_type = 2;</code>
     * @return The enum numeric value on the wire for sourceType.
     */
    int getSourceTypeValue();
    /**
     * <code>.SourceType source_type = 2;</code>
     * @return The sourceType.
     */
    ForwardProto.SourceType getSourceType();

    /**
     * <code>.Publication publication = 3;</code>
     * @return Whether the publication field is set.
     */
    boolean hasPublication();
    /**
     * <code>.Publication publication = 3;</code>
     * @return The publication.
     */
    PublicationProto.Publication getPublication();
    /**
     * <code>.Publication publication = 3;</code>
     */
    PublicationProto.PublicationOrBuilder getPublicationOrBuilder();
  }
  /**
   * Protobuf type {@code ForwardMessage}
   */
  public static final class ForwardMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:ForwardMessage)
      ForwardMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 31,
        /* patch= */ 1,
        /* suffix= */ "",
        ForwardMessage.class.getName());
    }
    // Use ForwardMessage.newBuilder() to construct.
    private ForwardMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private ForwardMessage() {
      source_ = "";
      sourceType_ = 0;
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ForwardProto.internal_static_ForwardMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ForwardProto.internal_static_ForwardMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ForwardProto.ForwardMessage.class, ForwardProto.ForwardMessage.Builder.class);
    }

    private int bitField0_;
    public static final int SOURCE_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile java.lang.Object source_ = "";
    /**
     * <code>string source = 1;</code>
     * @return The source.
     */
    @java.lang.Override
    public java.lang.String getSource() {
      java.lang.Object ref = source_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        source_ = s;
        return s;
      }
    }
    /**
     * <code>string source = 1;</code>
     * @return The bytes for source.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getSourceBytes() {
      java.lang.Object ref = source_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        source_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SOURCE_TYPE_FIELD_NUMBER = 2;
    private int sourceType_ = 0;
    /**
     * <code>.SourceType source_type = 2;</code>
     * @return The enum numeric value on the wire for sourceType.
     */
    @java.lang.Override public int getSourceTypeValue() {
      return sourceType_;
    }
    /**
     * <code>.SourceType source_type = 2;</code>
     * @return The sourceType.
     */
    @java.lang.Override public ForwardProto.SourceType getSourceType() {
      ForwardProto.SourceType result = ForwardProto.SourceType.forNumber(sourceType_);
      return result == null ? ForwardProto.SourceType.UNRECOGNIZED : result;
    }

    public static final int PUBLICATION_FIELD_NUMBER = 3;
    private PublicationProto.Publication publication_;
    /**
     * <code>.Publication publication = 3;</code>
     * @return Whether the publication field is set.
     */
    @java.lang.Override
    public boolean hasPublication() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.Publication publication = 3;</code>
     * @return The publication.
     */
    @java.lang.Override
    public PublicationProto.Publication getPublication() {
      return publication_ == null ? PublicationProto.Publication.getDefaultInstance() : publication_;
    }
    /**
     * <code>.Publication publication = 3;</code>
     */
    @java.lang.Override
    public PublicationProto.PublicationOrBuilder getPublicationOrBuilder() {
      return publication_ == null ? PublicationProto.Publication.getDefaultInstance() : publication_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(source_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 1, source_);
      }
      if (sourceType_ != ForwardProto.SourceType.BROKER.getNumber()) {
        output.writeEnum(2, sourceType_);
      }
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeMessage(3, getPublication());
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(source_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(1, source_);
      }
      if (sourceType_ != ForwardProto.SourceType.BROKER.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(2, sourceType_);
      }
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, getPublication());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ForwardProto.ForwardMessage)) {
        return super.equals(obj);
      }
      ForwardProto.ForwardMessage other = (ForwardProto.ForwardMessage) obj;

      if (!getSource()
          .equals(other.getSource())) return false;
      if (sourceType_ != other.sourceType_) return false;
      if (hasPublication() != other.hasPublication()) return false;
      if (hasPublication()) {
        if (!getPublication()
            .equals(other.getPublication())) return false;
      }
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + SOURCE_FIELD_NUMBER;
      hash = (53 * hash) + getSource().hashCode();
      hash = (37 * hash) + SOURCE_TYPE_FIELD_NUMBER;
      hash = (53 * hash) + sourceType_;
      if (hasPublication()) {
        hash = (37 * hash) + PUBLICATION_FIELD_NUMBER;
        hash = (53 * hash) + getPublication().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ForwardProto.ForwardMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ForwardProto.ForwardMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ForwardProto.ForwardMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ForwardProto.ForwardMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static ForwardProto.ForwardMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static ForwardProto.ForwardMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ForwardProto.ForwardMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code ForwardMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ForwardMessage)
        ForwardProto.ForwardMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ForwardProto.internal_static_ForwardMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ForwardProto.internal_static_ForwardMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ForwardProto.ForwardMessage.class, ForwardProto.ForwardMessage.Builder.class);
      }

      // Construct using ForwardProto.ForwardMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage
                .alwaysUseFieldBuilders) {
          internalGetPublicationFieldBuilder();
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        source_ = "";
        sourceType_ = 0;
        publication_ = null;
        if (publicationBuilder_ != null) {
          publicationBuilder_.dispose();
          publicationBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ForwardProto.internal_static_ForwardMessage_descriptor;
      }

      @java.lang.Override
      public ForwardProto.ForwardMessage getDefaultInstanceForType() {
        return ForwardProto.ForwardMessage.getDefaultInstance();
      }

      @java.lang.Override
      public ForwardProto.ForwardMessage build() {
        ForwardProto.ForwardMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public ForwardProto.ForwardMessage buildPartial() {
        ForwardProto.ForwardMessage result = new ForwardProto.ForwardMessage(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(ForwardProto.ForwardMessage result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.source_ = source_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.sourceType_ = sourceType_;
        }
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.publication_ = publicationBuilder_ == null
              ? publication_
              : publicationBuilder_.build();
          to_bitField0_ |= 0x00000001;
        }
        result.bitField0_ |= to_bitField0_;
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ForwardProto.ForwardMessage) {
          return mergeFrom((ForwardProto.ForwardMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ForwardProto.ForwardMessage other) {
        if (other == ForwardProto.ForwardMessage.getDefaultInstance()) return this;
        if (!other.getSource().isEmpty()) {
          source_ = other.source_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (other.sourceType_ != 0) {
          setSourceTypeValue(other.getSourceTypeValue());
        }
        if (other.hasPublication()) {
          mergePublication(other.getPublication());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                source_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 16: {
                sourceType_ = input.readEnum();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
              case 26: {
                input.readMessage(
                    internalGetPublicationFieldBuilder().getBuilder(),
                    extensionRegistry);
                bitField0_ |= 0x00000004;
                break;
              } // case 26
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private java.lang.Object source_ = "";
      /**
       * <code>string source = 1;</code>
       * @return The source.
       */
      public java.lang.String getSource() {
        java.lang.Object ref = source_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          source_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string source = 1;</code>
       * @return The bytes for source.
       */
      public com.google.protobuf.ByteString
          getSourceBytes() {
        java.lang.Object ref = source_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          source_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string source = 1;</code>
       * @param value The source to set.
       * @return This builder for chaining.
       */
      public Builder setSource(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        source_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string source = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearSource() {
        source_ = getDefaultInstance().getSource();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string source = 1;</code>
       * @param value The bytes for source to set.
       * @return This builder for chaining.
       */
      public Builder setSourceBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        source_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private int sourceType_ = 0;
      /**
       * <code>.SourceType source_type = 2;</code>
       * @return The enum numeric value on the wire for sourceType.
       */
      @java.lang.Override public int getSourceTypeValue() {
        return sourceType_;
      }
      /**
       * <code>.SourceType source_type = 2;</code>
       * @param value The enum numeric value on the wire for sourceType to set.
       * @return This builder for chaining.
       */
      public Builder setSourceTypeValue(int value) {
        sourceType_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.SourceType source_type = 2;</code>
       * @return The sourceType.
       */
      @java.lang.Override
      public ForwardProto.SourceType getSourceType() {
        ForwardProto.SourceType result = ForwardProto.SourceType.forNumber(sourceType_);
        return result == null ? ForwardProto.SourceType.UNRECOGNIZED : result;
      }
      /**
       * <code>.SourceType source_type = 2;</code>
       * @param value The sourceType to set.
       * @return This builder for chaining.
       */
      public Builder setSourceType(ForwardProto.SourceType value) {
        if (value == null) { throw new NullPointerException(); }
        bitField0_ |= 0x00000002;
        sourceType_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.SourceType source_type = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearSourceType() {
        bitField0_ = (bitField0_ & ~0x00000002);
        sourceType_ = 0;
        onChanged();
        return this;
      }

      private PublicationProto.Publication publication_;
      private com.google.protobuf.SingleFieldBuilder<
          PublicationProto.Publication, PublicationProto.Publication.Builder, PublicationProto.PublicationOrBuilder> publicationBuilder_;
      /**
       * <code>.Publication publication = 3;</code>
       * @return Whether the publication field is set.
       */
      public boolean hasPublication() {
        return ((bitField0_ & 0x00000004) != 0);
      }
      /**
       * <code>.Publication publication = 3;</code>
       * @return The publication.
       */
      public PublicationProto.Publication getPublication() {
        if (publicationBuilder_ == null) {
          return publication_ == null ? PublicationProto.Publication.getDefaultInstance() : publication_;
        } else {
          return publicationBuilder_.getMessage();
        }
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      public Builder setPublication(PublicationProto.Publication value) {
        if (publicationBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          publication_ = value;
        } else {
          publicationBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      public Builder setPublication(
          PublicationProto.Publication.Builder builderForValue) {
        if (publicationBuilder_ == null) {
          publication_ = builderForValue.build();
        } else {
          publicationBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      public Builder mergePublication(PublicationProto.Publication value) {
        if (publicationBuilder_ == null) {
          if (((bitField0_ & 0x00000004) != 0) &&
            publication_ != null &&
            publication_ != PublicationProto.Publication.getDefaultInstance()) {
            getPublicationBuilder().mergeFrom(value);
          } else {
            publication_ = value;
          }
        } else {
          publicationBuilder_.mergeFrom(value);
        }
        if (publication_ != null) {
          bitField0_ |= 0x00000004;
          onChanged();
        }
        return this;
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      public Builder clearPublication() {
        bitField0_ = (bitField0_ & ~0x00000004);
        publication_ = null;
        if (publicationBuilder_ != null) {
          publicationBuilder_.dispose();
          publicationBuilder_ = null;
        }
        onChanged();
        return this;
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      public PublicationProto.Publication.Builder getPublicationBuilder() {
        bitField0_ |= 0x00000004;
        onChanged();
        return internalGetPublicationFieldBuilder().getBuilder();
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      public PublicationProto.PublicationOrBuilder getPublicationOrBuilder() {
        if (publicationBuilder_ != null) {
          return publicationBuilder_.getMessageOrBuilder();
        } else {
          return publication_ == null ?
              PublicationProto.Publication.getDefaultInstance() : publication_;
        }
      }
      /**
       * <code>.Publication publication = 3;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          PublicationProto.Publication, PublicationProto.Publication.Builder, PublicationProto.PublicationOrBuilder> 
          internalGetPublicationFieldBuilder() {
        if (publicationBuilder_ == null) {
          publicationBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              PublicationProto.Publication, PublicationProto.Publication.Builder, PublicationProto.PublicationOrBuilder>(
                  getPublication(),
                  getParentForChildren(),
                  isClean());
          publication_ = null;
        }
        return publicationBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:ForwardMessage)
    }

    // @@protoc_insertion_point(class_scope:ForwardMessage)
    private static final ForwardProto.ForwardMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ForwardProto.ForwardMessage();
    }

    public static ForwardProto.ForwardMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ForwardMessage>
        PARSER = new com.google.protobuf.AbstractParser<ForwardMessage>() {
      @java.lang.Override
      public ForwardMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<ForwardMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ForwardMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public ForwardProto.ForwardMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ForwardMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_ForwardMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023proto/forward.proto\032\027proto/publication" +
      ".proto\"e\n\016ForwardMessage\022\016\n\006source\030\001 \001(\t" +
      "\022 \n\013source_type\030\002 \001(\0162\013.SourceType\022!\n\013pu" +
      "blication\030\003 \001(\0132\014.Publication*\'\n\nSourceT" +
      "ype\022\n\n\006BROKER\020\000\022\r\n\tPUBLISHER\020\001B\016B\014Forwar" +
      "dProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          PublicationProto.getDescriptor(),
        });
    internal_static_ForwardMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ForwardMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_ForwardMessage_descriptor,
        new java.lang.String[] { "Source", "SourceType", "Publication", });
    descriptor.resolveAllFeaturesImmutable();
    PublicationProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
