/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.jta.atomikos;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bean friendly variant of
 * <a href="http://www.atomikos.com/Documentation/JtaProperties">Atomikos configuration
 * properties</a>. Allows for setter based configuration and is amiable to relaxed data
 * binding.
 *
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @since 1.2.0
 * @see #asProperties()
 */
@ConfigurationProperties(prefix = "spring.jta.atomikos.properties")
public class AtomikosProperties {

	private final Map<String, String> values = new TreeMap<String, String>();

	/**
	 * Transaction manager implementation that should be started.
	 */
	private String service;

	/**
	 * Maximum timeout (in milliseconds) that can be allowed for transactions.
	 */
	private long maxTimeout = 300000;

	/**
	 * Default timeout for JTA transactions.
	 */
	private long defaultJtaTimeout = 10000;

	/**
	 * Maximum number of active transactions.
	 */
	private int maxActives = 50;

	/**
	 * Enable disk logging.
	 */
	private boolean enableLogging = true;

	/**
	 * Transaction manager's unique name. Defaults to the machine's IP address. If you
	 * plan to run more than one transaction manager against one database you must set
	 * this property to a unique value.
	 */
	private String transactionManagerUniqueName;

	/**
	 * Specify if sub-transactions should be joined when possible.
	 */
	private boolean serialJtaTransactions = true;

	/**
	 * Specify if a VM shutdown should trigger forced shutdown of the transaction core.
	 */
	private boolean forceShutdownOnVmExit;

	/**
	 * Transactions log file base name.
	 */
	private String logBaseName = "tmlog";

	/**
	 * Directory in which the log files should be stored. Defaults to the current working
	 * directory.
	 */
	private String logBaseDir;

	/**
	 * Interval between checkpoints. A checkpoint reduces the log file size at the expense
	 * of adding some overhead in the runtime.
	 */
	private long checkpointInterval = 500;

	/**
	 * Console log level.
	 */
	private AtomikosLoggingLevel consoleLogLevel = AtomikosLoggingLevel.WARN;

	/**
	 * Directory in which to store the debug log files. Defaults to the current working
	 * directory.
	 */
	private String outputDir;

	/**
	 * Debug logs file name.
	 */
	private String consoleFileName = "tm.out";

	/**
	 * Number of debug logs files that can be created.
	 */
	private int consoleFileCount = 1;

	/**
	 * How many bytes can be stored at most in debug logs files. Negative values means
	 * unlimited.
	 */
	private int consoleFileLimit = -1;

	/**
	 * Use different (and concurrent) threads for two-phase commit on the participating
	 * resources.
	 */
	private boolean threadedTwoPhaseCommit = true;

	/**
	 * Specifies the transaction manager implementation that should be started. There is
	 * no default value and this must be set. Generally,
	 * {@literal com.atomikos.icatch.standalone.UserTransactionServiceFactory} is the
	 * value you should set.
	 * @param service the service
	 */
	public void setService(String service) {
		this.service = service;
		set("service", service);
	}

	public String getService() {
		return this.service;
	}

	/**
	 * Specifies the maximum timeout (in milliseconds) that can be allowed for
	 * transactions. Defaults to {@literal 300000}. This means that calls to
	 * UserTransaction.setTransactionTimeout() with a value higher than configured here
	 * will be max'ed to this value.
	 * @param maxTimeout the max timeout
	 */
	public void setMaxTimeout(long maxTimeout) {
		this.maxTimeout = maxTimeout;
		set("max_timeout", maxTimeout);
	}

	public long getMaxTimeout() {
		return this.maxTimeout;
	}

	/**
	 * The default timeout for JTA transactions (optional, defaults to {@literal 10000}
	 * ms).
	 * @param defaultJtaTimeout the default JTA timeout
	 */
	public void setDefaultJtaTimeout(long defaultJtaTimeout) {
		this.defaultJtaTimeout = defaultJtaTimeout;
		set("default_jta_timeout", defaultJtaTimeout);
	}

	public long getDefaultJtaTimeout() {
		return this.defaultJtaTimeout;
	}

	/**
	 * Specifies the maximum number of active transactions. Defaults to {@literal 50}. A
	 * negative value means infinite amount. You will get an {@code IllegalStateException}
	 * with error message "Max number of active transactions reached" if you call
	 * {@code UserTransaction.begin()} while there are already n concurrent transactions
	 * running, n being this value.
	 * @param maxActives the max activities
	 */
	public void setMaxActives(int maxActives) {
		this.maxActives = maxActives;
		set("max_actives", maxActives);
	}

	public int getMaxActives() {
		return this.maxActives;
	}

	/**
	 * Specifies if disk logging should be enabled or not. Defaults to true. It is useful
	 * for JUnit testing, or to profile code without seeing the transaction manager's
	 * activity as a hot spot but this should never be disabled on production or data
	 * integrity cannot be guaranteed.
	 * @param enableLogging if logging is enabled
	 */
	public void setEnableLogging(boolean enableLogging) {
		this.enableLogging = enableLogging;
		set("enable_logging", enableLogging);
	}

	public boolean isEnableLogging() {
		return this.enableLogging;
	}

	/**
	 * Specifies the transaction manager's unique name. Defaults to the machine's IP
	 * address. If you plan to run more than one transaction manager against one database
	 * you must set this property to a unique value or you might run into duplicate
	 * transaction ID (XID) problems that can be quite subtle (example:
	 * {@literal http://fogbugz.atomikos.com/default.asp?community.6.2225.7}). If multiple
	 * instances need to use the same properties file then the easiest way to ensure
	 * uniqueness for this property is by referencing a system property specified at VM
	 * startup.
	 * @param uniqueName the unique name
	 */
	public void setTransactionManagerUniqueName(String uniqueName) {
		this.transactionManagerUniqueName = uniqueName;
		set("tm_unique_name", uniqueName);
	}

	public String getTransactionManagerUniqueName() {
		return this.transactionManagerUniqueName;
	}

	/**
	 * Specifies if subtransactions should be joined when possible. Defaults to true. When
	 * false, no attempt to call {@code XAResource.start(TM_JOIN)} will be made for
	 * different but related subtransactions. This setting has no effect on resource
	 * access within one and the same transaction. If you don't use subtransactions then
	 * this setting can be ignored.
	 * @param serialJtaTransactions if serial JTA transaction are supported
	 */
	public void setSerialJtaTransactions(boolean serialJtaTransactions) {
		this.serialJtaTransactions = serialJtaTransactions;
		set("serial_jta_transactions", serialJtaTransactions);
	}

	public boolean isSerialJtaTransactions() {
		return this.serialJtaTransactions;
	}

	/**
	 * Specifies whether VM shutdown should trigger forced shutdown of the transaction
	 * core. Defaults to false.
	 * @param forceShutdownOnVmExit if VM shutdown should be forced
	 */
	public void setForceShutdownOnVmExit(boolean forceShutdownOnVmExit) {
		this.forceShutdownOnVmExit = forceShutdownOnVmExit;
		set("force_shutdown_on_vm_exit", forceShutdownOnVmExit);
	}

	public boolean isForceShutdownOnVmExit() {
		return this.forceShutdownOnVmExit;
	}

	/**
	 * Specifies the transactions log file base name. Defaults to {@literal tmlog}. The
	 * transactions logs are stored in files using this name appended with a number and
	 * the extension {@literal .log}. At checkpoint, a new transactions log file is
	 * created and the number is incremented.
	 * @param logBaseName the log base name
	 */
	public void setLogBaseName(String logBaseName) {
		this.logBaseName = logBaseName;
		set("log_base_name", logBaseName);
	}

	public String getLogBaseName() {
		return this.logBaseName;
	}

	/**
	 * Specifies the directory in which the log files should be stored. Defaults to the
	 * current working directory. This directory should be a stable storage like a SAN,
	 * RAID or at least backed up location. The transactions logs files are as important
	 * as the data themselves to guarantee consistency in case of failures.
	 * @param logBaseDir the log base dir
	 */
	public void setLogBaseDir(String logBaseDir) {
		this.logBaseDir = logBaseDir;
		set("log_base_dir", logBaseDir);
	}

	public String getLogBaseDir() {
		return this.logBaseDir;
	}

	/**
	 * Specifies the interval between checkpoints. A checkpoint reduces the log file size
	 * at the expense of adding some overhead in the runtime. Defaults to {@literal 500}.
	 * @param checkpointInterval the checkpoint interval
	 */
	public void setCheckpointInterval(long checkpointInterval) {
		this.checkpointInterval = checkpointInterval;
		set("checkpoint_interval", checkpointInterval);
	}

	public long getCheckpointInterval() {
		return this.checkpointInterval;
	}

	/**
	 * Specifies the console log level. Defaults to {@link AtomikosLoggingLevel#WARN}.
	 * @param consoleLogLevel the console log level
	 */
	public void setConsoleLogLevel(AtomikosLoggingLevel consoleLogLevel) {
		this.consoleLogLevel = consoleLogLevel;
		set("console_log_level", consoleLogLevel);
	}

	public AtomikosLoggingLevel getConsoleLogLevel() {
		return this.consoleLogLevel;
	}

	/**
	 * Specifies the directory in which to store the debug log files. Defaults to the
	 * current working directory.
	 * @param outputDir the output dir
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
		set("output_dir", outputDir);
	}

	public String getOutputDir() {
		return this.outputDir;
	}

	/**
	 * Specifies the debug logs file name. Defaults to {@literal tm.out}.
	 * @param consoleFileName the console file name
	 */
	public void setConsoleFileName(String consoleFileName) {
		this.consoleFileName = consoleFileName;
		set("console_file_name", consoleFileName);
	}

	public String getConsoleFileName() {
		return this.consoleFileName;
	}

	/**
	 * Specifies how many debug logs files can be created. Defaults to {@literal 1}.
	 * @param consoleFileCount the console file count
	 */
	public void setConsoleFileCount(int consoleFileCount) {
		this.consoleFileCount = consoleFileCount;
		set("console_file_count", consoleFileCount);
	}

	public int getConsoleFileCount() {
		return this.consoleFileCount;
	}

	/**
	 * Specifies how many bytes can be stored at most in debug logs files. Defaults to
	 * {@literal -1}. Negative values means unlimited.
	 * @param consoleFileLimit the console file limit
	 */
	public void setConsoleFileLimit(int consoleFileLimit) {
		this.consoleFileLimit = consoleFileLimit;
		set("console_file_limit", consoleFileLimit);
	}

	public int getConsoleFileLimit() {
		return this.consoleFileLimit;
	}

	/**
	 * Specifies whether or not to use different (and concurrent) threads for two-phase
	 * commit on the participating resources. Setting this to {@literal true} implies that
	 * the commit is more efficient since waiting for acknowledgements is done in
	 * parallel. Defaults to {@literal true}. If you set this to {@literal false}, then
	 * commits will happen in the order that resources are accessed within the
	 * transaction.
	 * @param threadedTwoPhaseCommit if threaded two phase commits should be used
	 */
	public void setThreadedTwoPhaseCommit(boolean threadedTwoPhaseCommit) {
		this.threadedTwoPhaseCommit = threadedTwoPhaseCommit;
		set("threaded_2pc", threadedTwoPhaseCommit);
	}

	public boolean isThreadedTwoPhaseCommit() {
		return this.threadedTwoPhaseCommit;
	}

	private void set(String key, Object value) {
		set("com.atomikos.icatch.", key, value);
	}

	private void set(String keyPrefix, String key, Object value) {
		if (value != null) {
			this.values.put(keyPrefix + key, value.toString());
		}
		else {
			this.values.remove(keyPrefix + key);
		}
	}

	/**
	 * Returns the properties as a {@link Properties} object that can be used with
	 * Atomikos.
	 * @return the properties
	 */
	public Properties asProperties() {
		Properties properties = new Properties();
		properties.putAll(this.values);
		return properties;
	}

}
