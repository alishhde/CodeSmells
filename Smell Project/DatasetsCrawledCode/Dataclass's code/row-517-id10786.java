 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "", propOrder = {"gateways"})
 public static class GatewayHub {


 @XmlElement(name = "gateway", namespace = "http://geode.apache.org/schema/cache")
 protected List<Gateway> gateways;
 @XmlAttribute(name = "id", required = true)
 protected String id;
 @XmlAttribute(name = "bind-address")
 protected String bindAddress;
 @XmlAttribute(name = "maximum-time-between-pings")
 protected String maximumTimeBetweenPings;
 @XmlAttribute(name = "port")
 protected String port;
 @XmlAttribute(name = "socket-buffer-size")
 protected String socketBufferSize;
 @XmlAttribute(name = "startup-policy")
 protected String startupPolicy;
 @XmlAttribute(name = "manual-start")
 protected Boolean manualStart;
 @XmlAttribute(name = "max-connections")
 protected BigInteger maxConnections;


 /**
     * Gets the value of the gateway property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gateway property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getGateway().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CacheConfig.GatewayHub.Gateway }
     *
     *
     */
 public List<Gateway> getGateway() {
 if (gateways == null) {
 gateways = new ArrayList<Gateway>();
      }
 return this.gateways;
    }


 /**
     * Gets the value of the id property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getId() {
 return id;
    }


 /**
     * Sets the value of the id property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setId(String value) {
 this.id = value;
    }


 /**
     * Gets the value of the bindAddress property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getBindAddress() {
 return bindAddress;
    }


 /**
     * Sets the value of the bindAddress property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setBindAddress(String value) {
 this.bindAddress = value;
    }


 /**
     * Gets the value of the maximumTimeBetweenPings property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getMaximumTimeBetweenPings() {
 return maximumTimeBetweenPings;
    }


 /**
     * Sets the value of the maximumTimeBetweenPings property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setMaximumTimeBetweenPings(String value) {
 this.maximumTimeBetweenPings = value;
    }


 /**
     * Gets the value of the port property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getPort() {
 return port;
    }


 /**
     * Sets the value of the port property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setPort(String value) {
 this.port = value;
    }


 /**
     * Gets the value of the socketBufferSize property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getSocketBufferSize() {
 return socketBufferSize;
    }


 /**
     * Sets the value of the socketBufferSize property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setSocketBufferSize(String value) {
 this.socketBufferSize = value;
    }


 /**
     * Gets the value of the startupPolicy property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getStartupPolicy() {
 return startupPolicy;
    }


 /**
     * Sets the value of the startupPolicy property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setStartupPolicy(String value) {
 this.startupPolicy = value;
    }


 /**
     * Gets the value of the manualStart property.
     *
     * possible object is
     * {@link Boolean }
     *
     */
 public Boolean isManualStart() {
 return manualStart;
    }


 /**
     * Sets the value of the manualStart property.
     *
     * allowed object is
     * {@link Boolean }
     *
     */
 public void setManualStart(Boolean value) {
 this.manualStart = value;
    }


 /**
     * Gets the value of the maxConnections property.
     *
     * possible object is
     * {@link BigInteger }
     *
     */
 public BigInteger getMaxConnections() {
 return maxConnections;
    }


 /**
     * Sets the value of the maxConnections property.
     *
     * allowed object is
     * {@link BigInteger }
     *
     */
 public void setMaxConnections(BigInteger value) {
 this.maxConnections = value;
    }




 /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;choice>
     *           &lt;element name="gateway-endpoint" maxOccurs="unbounded">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;attribute name="host" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                   &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                   &lt;attribute name="port" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="gateway-listener" maxOccurs="unbounded">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="class-name" type="{http://geode.apache.org/schema/cache}class-name-type"/>
     *                     &lt;element name="parameter" type="{http://geode.apache.org/schema/cache}parameter-type" maxOccurs="unbounded" minOccurs="0"/>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *         &lt;/choice>
     *         &lt;element name="gateway-queue" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="alert-threshold" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="batch-conflation" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="batch-size" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="batch-time-interval" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="enable-persistence" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="disk-store-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="roll-oplogs" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="maximum-queue-memory" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="overflow-directory" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="early-ack" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="socket-buffer-size" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="socket-read-timeout" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="concurrency-level" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="order-policy" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "", propOrder = {"gatewayEndpoints", "gatewayListeners", "gatewayQueue"})
 public static class Gateway {


 @XmlElement(name = "gateway-endpoint", namespace = "http://geode.apache.org/schema/cache")
 protected List<GatewayEndpoint> gatewayEndpoints;
 @XmlElement(name = "gateway-listener", namespace = "http://geode.apache.org/schema/cache")
 protected List<DeclarableType> gatewayListeners;
 @XmlElement(name = "gateway-queue", namespace = "http://geode.apache.org/schema/cache")
 protected CacheConfig.GatewayHub.Gateway.GatewayQueue gatewayQueue;
 @XmlAttribute(name = "early-ack")
 protected Boolean earlyAck;
 @XmlAttribute(name = "id", required = true)
 protected String id;
 @XmlAttribute(name = "socket-buffer-size")
 protected String socketBufferSize;
 @XmlAttribute(name = "socket-read-timeout")
 protected String socketReadTimeout;
 @XmlAttribute(name = "concurrency-level")
 protected String concurrencyLevel;
 @XmlAttribute(name = "order-policy")
 protected String orderPolicy;


 /**
       * Gets the value of the gatewayEndpoints property.
       *
       * <p>
       * This accessor method returns a reference to the live list,
       * not a snapshot. Therefore any modification you make to the
       * returned list will be present inside the JAXB object.
       * This is why there is not a <CODE>set</CODE> method for the gatewayEndpoints property.
       *
       * <p>
       * For example, to add a new item, do as follows:
       *
       * <pre>
       * getGatewayEndpoints().add(newItem);
       * </pre>
       *
       *
       * <p>
       * Objects of the following type(s) are allowed in the list
       * {@link CacheConfig.GatewayHub.Gateway.GatewayEndpoint }
       *
       *
       */
 public List<GatewayEndpoint> getGatewayEndpoints() {
 if (gatewayEndpoints == null) {
 gatewayEndpoints = new ArrayList<GatewayEndpoint>();
        }
 return this.gatewayEndpoints;
      }


 /**
       * Gets the value of the gatewayListeners property.
       *
       * <p>
       * This accessor method returns a reference to the live list,
       * not a snapshot. Therefore any modification you make to the
       * returned list will be present inside the JAXB object.
       * This is why there is not a <CODE>set</CODE> method for the gatewayListeners property.
       *
       * <p>
       * For example, to add a new item, do as follows:
       *
       * <pre>
       * getGatewayListeners().add(newItem);
       * </pre>
       *
       *
       * <p>
       * Objects of the following type(s) are allowed in the list
       * {@link DeclarableType }
       *
       *
       */
 public List<DeclarableType> getGatewayListeners() {
 if (gatewayListeners == null) {
 gatewayListeners = new ArrayList<DeclarableType>();
        }
 return this.gatewayListeners;
      }


 /**
       * Gets the value of the gatewayQueue property.
       *
       * possible object is
       * {@link CacheConfig.GatewayHub.Gateway.GatewayQueue }
       *
       */
 public CacheConfig.GatewayHub.Gateway.GatewayQueue getGatewayQueue() {
 return gatewayQueue;
      }


 /**
       * Sets the value of the gatewayQueue property.
       *
       * allowed object is
       * {@link CacheConfig.GatewayHub.Gateway.GatewayQueue }
       *
       */
 public void setGatewayQueue(CacheConfig.GatewayHub.Gateway.GatewayQueue value) {
 this.gatewayQueue = value;
      }


 /**
       * Gets the value of the earlyAck property.
       *
       * possible object is
       * {@link Boolean }
       *
       */
 public Boolean isEarlyAck() {
 return earlyAck;
      }


 /**
       * Sets the value of the earlyAck property.
       *
       * allowed object is
       * {@link Boolean }
       *
       */
 public void setEarlyAck(Boolean value) {
 this.earlyAck = value;
      }


 /**
       * Gets the value of the id property.
       *
       * possible object is
       * {@link String }
       *
       */
 public String getId() {
 return id;
      }


 /**
       * Sets the value of the id property.
       *
       * allowed object is
       * {@link String }
       *
       */
 public void setId(String value) {
 this.id = value;
      }


 /**
       * Gets the value of the socketBufferSize property.
       *
       * possible object is
       * {@link String }
       *
       */
 public String getSocketBufferSize() {
 return socketBufferSize;
      }


 /**
       * Sets the value of the socketBufferSize property.
       *
       * allowed object is
       * {@link String }
       *
       */
 public void setSocketBufferSize(String value) {
 this.socketBufferSize = value;
      }


 /**
       * Gets the value of the socketReadTimeout property.
       *
       * possible object is
       * {@link String }
       *
       */
 public String getSocketReadTimeout() {
 return socketReadTimeout;
      }


 /**
       * Sets the value of the socketReadTimeout property.
       *
       * allowed object is
       * {@link String }
       *
       */
 public void setSocketReadTimeout(String value) {
 this.socketReadTimeout = value;
      }


 /**
       * Gets the value of the concurrencyLevel property.
       *
       * possible object is
       * {@link String }
       *
       */
 public String getConcurrencyLevel() {
 return concurrencyLevel;
      }


 /**
       * Sets the value of the concurrencyLevel property.
       *
       * allowed object is
       * {@link String }
       *
       */
 public void setConcurrencyLevel(String value) {
 this.concurrencyLevel = value;
      }


 /**
       * Gets the value of the orderPolicy property.
       *
       * possible object is
       * {@link String }
       *
       */
 public String getOrderPolicy() {
 return orderPolicy;
      }


 /**
       * Sets the value of the orderPolicy property.
       *
       * allowed object is
       * {@link String }
       *
       */
 public void setOrderPolicy(String value) {
 this.orderPolicy = value;
      }




 /**
       * <p>
       * Java class for anonymous complex type.
       *
       * <p>
       * The following schema fragment specifies the expected content contained within this class.
       *
       * <pre>
       * &lt;complexType>
       *   &lt;complexContent>
       *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
       *       &lt;attribute name="host" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="port" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
       *     &lt;/restriction>
       *   &lt;/complexContent>
       * &lt;/complexType>
       * </pre>
       *
       *
       */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "")
 public static class GatewayEndpoint {


 @XmlAttribute(name = "host", required = true)
 protected String host;
 @XmlAttribute(name = "id", required = true)
 protected String id;
 @XmlAttribute(name = "port", required = true)
 protected String port;


 /**
         * Gets the value of the host property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getHost() {
 return host;
        }


 /**
         * Sets the value of the host property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setHost(String value) {
 this.host = value;
        }


 /**
         * Gets the value of the id property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getId() {
 return id;
        }


 /**
         * Sets the value of the id property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setId(String value) {
 this.id = value;
        }


 /**
         * Gets the value of the port property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getPort() {
 return port;
        }


 /**
         * Sets the value of the port property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setPort(String value) {
 this.port = value;
        }


      }


 /**
       * <p>
       * Java class for anonymous complex type.
       *
       * <p>
       * The following schema fragment specifies the expected content contained within this class.
       *
       * <pre>
       * &lt;complexType>
       *   &lt;complexContent>
       *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
       *       &lt;attribute name="alert-threshold" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="batch-conflation" type="{http://www.w3.org/2001/XMLSchema}boolean" />
       *       &lt;attribute name="batch-size" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="batch-time-interval" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="enable-persistence" type="{http://www.w3.org/2001/XMLSchema}boolean" />
       *       &lt;attribute name="disk-store-name" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="roll-oplogs" type="{http://www.w3.org/2001/XMLSchema}boolean" />
       *       &lt;attribute name="maximum-queue-memory" type="{http://www.w3.org/2001/XMLSchema}string" />
       *       &lt;attribute name="overflow-directory" type="{http://www.w3.org/2001/XMLSchema}string" />
       *     &lt;/restriction>
       *   &lt;/complexContent>
       * &lt;/complexType>
       * </pre>
       *
       *
       */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "")
 public static class GatewayQueue {


 @XmlAttribute(name = "alert-threshold")
 protected String alertThreshold;
 @XmlAttribute(name = "batch-conflation")
 protected Boolean batchConflation;
 @XmlAttribute(name = "batch-size")
 protected String batchSize;
 @XmlAttribute(name = "batch-time-interval")
 protected String batchTimeInterval;
 @XmlAttribute(name = "enable-persistence")
 protected Boolean enablePersistence;
 @XmlAttribute(name = "disk-store-name")
 protected String diskStoreName;
 @XmlAttribute(name = "roll-oplogs")
 protected Boolean rollOplogs;
 @XmlAttribute(name = "maximum-queue-memory")
 protected String maximumQueueMemory;
 @XmlAttribute(name = "overflow-directory")
 protected String overflowDirectory;


 /**
         * Gets the value of the alertThreshold property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getAlertThreshold() {
 return alertThreshold;
        }


 /**
         * Sets the value of the alertThreshold property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setAlertThreshold(String value) {
 this.alertThreshold = value;
        }


 /**
         * Gets the value of the batchConflation property.
         *
         * possible object is
         * {@link Boolean }
         *
         */
 public Boolean isBatchConflation() {
 return batchConflation;
        }


 /**
         * Sets the value of the batchConflation property.
         *
         * allowed object is
         * {@link Boolean }
         *
         */
 public void setBatchConflation(Boolean value) {
 this.batchConflation = value;
        }


 /**
         * Gets the value of the batchSize property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getBatchSize() {
 return batchSize;
        }


 /**
         * Sets the value of the batchSize property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setBatchSize(String value) {
 this.batchSize = value;
        }


 /**
         * Gets the value of the batchTimeInterval property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getBatchTimeInterval() {
 return batchTimeInterval;
        }


 /**
         * Sets the value of the batchTimeInterval property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setBatchTimeInterval(String value) {
 this.batchTimeInterval = value;
        }


 /**
         * Gets the value of the enablePersistence property.
         *
         * possible object is
         * {@link Boolean }
         *
         */
 public Boolean isEnablePersistence() {
 return enablePersistence;
        }


 /**
         * Sets the value of the enablePersistence property.
         *
         * allowed object is
         * {@link Boolean }
         *
         */
 public void setEnablePersistence(Boolean value) {
 this.enablePersistence = value;
        }


 /**
         * Gets the value of the diskStoreName property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getDiskStoreName() {
 return diskStoreName;
        }


 /**
         * Sets the value of the diskStoreName property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setDiskStoreName(String value) {
 this.diskStoreName = value;
        }


 /**
         * Gets the value of the rollOplogs property.
         *
         * possible object is
         * {@link Boolean }
         *
         */
 public Boolean isRollOplogs() {
 return rollOplogs;
        }


 /**
         * Sets the value of the rollOplogs property.
         *
         * allowed object is
         * {@link Boolean }
         *
         */
 public void setRollOplogs(Boolean value) {
 this.rollOplogs = value;
        }


 /**
         * Gets the value of the maximumQueueMemory property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getMaximumQueueMemory() {
 return maximumQueueMemory;
        }


 /**
         * Sets the value of the maximumQueueMemory property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setMaximumQueueMemory(String value) {
 this.maximumQueueMemory = value;
        }


 /**
         * Gets the value of the overflowDirectory property.
         *
         * possible object is
         * {@link String }
         *
         */
 public String getOverflowDirectory() {
 return overflowDirectory;
        }


 /**
         * Sets the value of the overflowDirectory property.
         *
         * allowed object is
         * {@link String }
         *
         */
 public void setOverflowDirectory(String value) {
 this.overflowDirectory = value;
        }


      }


    }


  }