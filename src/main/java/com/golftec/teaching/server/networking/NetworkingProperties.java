package com.golftec.teaching.server.networking;

import com.golftec.teaching.server.networking.connection.Endpoint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * NOTE: copied from -server project
 */
public class NetworkingProperties {

    private static String LOAD_BALANCE_SERVER_PROPERTIES = "loadBalanceServer.prop";
    private static NetworkingProperties props;
    private Properties properties;
    private EndpointFormat epFormatter;

    private NetworkingProperties() {
        epFormatter = new EndpointFormat(new ArrayList<>());
        properties = new Properties();
        try {
            FileInputStream f = new FileInputStream(NetworkingProperties.LOAD_BALANCE_SERVER_PROPERTIES);
            properties.load(f);
            f.close();
        } catch (Exception e) {
            commitProperties("Default");
            e.printStackTrace();
        }
    }

    public static NetworkingProperties getNetworkingProperties() {
        if (props == null) {
            props = new NetworkingProperties();
        }
        return props;
    }

    public void commitProperties(String comments) {
        try {
            FileOutputStream f = new FileOutputStream(NetworkingProperties.LOAD_BALANCE_SERVER_PROPERTIES);
            properties.store(f, comments);
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLoadBalanceIp() {
        return properties.getProperty("ipaddress");
    }

    public void setLoadBalanceIp(String ip) {
        properties.put("ipaddress", ip);
    }

    public int getLoadBalancePort() {
        if (properties.get("port") == null) {
            return -1;
        }
        return Integer.parseInt(properties.getProperty("port"));
    }

    public void setLoadBalancePort(int port) {
        properties.put("port", "" + port);
    }

    public int getLoadBalanceCallbackPort() {
        if (properties.get("callbackport") == null) {
            return -1;
        }
        return Integer.parseInt(properties.getProperty("callbackport"));
    }

    public void setLoadBalanceCallbackPort(int port) {
        properties.put("callbackport", "" + port);
    }

    public void setDatabaseServerEndpoint(Endpoint endpoint) {
        properties.put("dbendpoint", epFormatter.getEndpointFormattedString(endpoint));
    }

    public Endpoint getDBServerEndpoint() {
        return epFormatter.getEndpointFromFormattedString(properties.getProperty("dbendpoint"));
    }

    public void setWorkingServerEndpoint(Endpoint endpoint) {
        properties.put("workingserverendpoint", epFormatter.addEndpointToList(endpoint));
    }

    public List<Endpoint> getWorkingServerEndpoints() {
        String formattedString = properties.getProperty("workingserverendpoint");
        if (formattedString == null) {
            return new ArrayList<>();
        }
        return epFormatter.parseWorkflowServerStringToList(formattedString);
    }

    private class EndpointFormat {

        private List<Endpoint> endpoints;

        public EndpointFormat(List<Endpoint> endpoints) {
            this.endpoints = endpoints;
        }

        public String addEndpointToList(Endpoint endpoint) {
            if (endpoints == null) {
                endpoints = new ArrayList<>();
            }
            if (!endpointExists(endpoint)) {
                endpoints.add(endpoint);
            }
            return endpointFormattedString();
        }

        public boolean endpointExists(Endpoint endpoint) {
            boolean exists = false;
            for (Endpoint ep : endpoints) {
                if (ep.getIp().equals(endpoint.getIp())
                    && ep.getPort() == endpoint.getPort()) {
                    exists = true;
                    break;
                }
            }
            return exists;
        }

        public List<Endpoint> parseWorkflowServerStringToList(String formattedString) {
            String[] ipAndPort = formattedString.split(",");
            endpoints = new ArrayList<>();
            for (String s : ipAndPort) {
                String[] epString = s.split(":");
                Endpoint endpoint = new Endpoint();
                endpoint.setIp(epString[0].trim());
                endpoint.setPort(Integer.parseInt(epString[1].trim()));
                endpoints.add(endpoint);
            }
            return endpoints;
        }

        public String getEndpointFormattedString(Endpoint endpoint) {
            return endpoint.getIp() + ":" + endpoint.getPort();
        }

        public Endpoint getEndpointFromFormattedString(String endpoint) {
            Endpoint ep = new Endpoint();
            String[] s = endpoint.split(":");
            ep.setIp(s[0]);
            ep.setPort(Integer.parseInt(s[1]));
            return ep;
        }

        private String endpointFormattedString() {
            String formattedString = null;
            for (int i = 0; i < endpoints.size(); i++) {
                Endpoint endpoint = endpoints.get(i);
                if (i != 0) {
                    formattedString = formattedString + "," + endpoint.getIp() + ":" + endpoint.getPort();
                } else {
                    formattedString = endpoint.getIp() + ":" + endpoint.getPort();
                }
            }
            return formattedString;
        }
    }
}
