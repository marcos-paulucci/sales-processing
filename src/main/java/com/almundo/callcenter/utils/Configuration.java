package com.almundo.callcenter.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Marcos on 12/1/2017.
 */
public class Configuration {

	private static Configuration instance = null;
	private Integer threads;
	private Integer operadores;
	private Integer supervisores;
	private Integer directores;
	private Integer minRandCallTime;
	private Integer maxRandCallTime;

	private Properties loadConfigFile(){
		String resourceName = "config.properties"; // could also be a constant
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties prop = new Properties();
		InputStream resourceStream = null;
		try {
			resourceStream = loader.getResourceAsStream(resourceName);
			prop.load(resourceStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (resourceStream != null) {
				try {
					resourceStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	public Configuration() {
		Properties props = this.loadConfigFile();
		this.threads = Integer.parseInt(props.getProperty("threads"));
		this.operadores = Integer.parseInt(props.getProperty("operadores"));
		this.supervisores = Integer.parseInt(props.getProperty("supervisores"));
		this.directores = Integer.parseInt(props.getProperty("directores"));
		this.minRandCallTime = Integer.parseInt(props.getProperty("minRandCallTimeMilis"));
		this.maxRandCallTime = Integer.parseInt(props.getProperty("maxRandCallTimeMilis"));
	}

	public Configuration(int threads, int op, int sup, int dir) {
		Properties props = this.loadConfigFile();
		this.threads = threads;
		this.operadores = op;
		this.supervisores = sup;
		this.directores = dir;
		this.minRandCallTime = Integer.parseInt(props.getProperty("minRandCallTimeMilis"));
		this.maxRandCallTime = Integer.parseInt(props.getProperty("maxRandCallTimeMilis"));
	}


	public Integer getDirectores() {
		return directores;
	}

	public Integer getThreads() {
		return threads;
	}

	public Integer getOperadores() {
		return operadores;
	}

	public Integer getSupervisores() {
		return supervisores;
	}

	public Integer getMinRandCallTime() {
		return minRandCallTime;
	}

	public void setMinRandCallTime(Integer minRandCallTime) {
		this.minRandCallTime = minRandCallTime;
	}

	public Integer getMaxRandCallTime() {
		return maxRandCallTime;
	}

	public void setMaxRandCallTime(Integer maxRandCallTime) {
		this.maxRandCallTime = maxRandCallTime;
	}
}
