package com.synterra.lens.entity;

import java.util.List;

public class EndpointConfig {
    private String path;
    private List<String> methods;
    private List<String> authorities;
    
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    
    public List<String> getMethods() { return methods; }
    public void setMethods(List<String> methods) { this.methods = methods; }
    
    public List<String> getAuthorities() { return authorities; }
    public void setAuthorities(List<String> authorities) { this.authorities = authorities; }
}