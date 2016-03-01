// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.monitor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JMXServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        ShowDataRunnable sdr = new ShowDataRunnable(out);
        MonitoringTools mt = new MonitoringTools(out);
        mt.foreachVM(sdr);
    }
}
