package com.capstone.booking.config.security;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CorsFilterTest {

    private CorsFilter corsFilterUnderTest;

    @Before
    public void setUp() {
        corsFilterUnderTest = new CorsFilter();
    }

    @Test
    public void testInit() throws Exception {
        // Setup
        final FilterConfig filterConfig = null;

        // Run the test
        corsFilterUnderTest.init(filterConfig);

        // Verify the results
    }

    @Test
    public void testInit_ThrowsServletException() {
        // Setup
        final FilterConfig filterConfig = null;

        // Run the test
        assertThatThrownBy(() -> {
            corsFilterUnderTest.init(filterConfig);
        }).isInstanceOf(ServletException.class).hasMessageContaining("message");
    }

    @Test
    public void testDoFilter() throws Exception {
        // Setup
        final ServletRequest servletRequest = null;
        final ServletResponse servletResponse = null;
        final FilterChain filterChain = null;

        // Run the test
        corsFilterUnderTest.doFilter(servletRequest, servletResponse, filterChain);

        // Verify the results
    }

    @Test
    public void testDoFilter_ThrowsIOException() {
        // Setup
        final ServletRequest servletRequest = null;
        final ServletResponse servletResponse = null;
        final FilterChain filterChain = null;

        // Run the test
        assertThatThrownBy(() -> {
            corsFilterUnderTest.doFilter(servletRequest, servletResponse, filterChain);
        }).isInstanceOf(IOException.class).hasMessageContaining("message");
    }

    @Test
    public void testDoFilter_ThrowsServletException() {
        // Setup
        final ServletRequest servletRequest = null;
        final ServletResponse servletResponse = null;
        final FilterChain filterChain = null;

        // Run the test
        assertThatThrownBy(() -> {
            corsFilterUnderTest.doFilter(servletRequest, servletResponse, filterChain);
        }).isInstanceOf(ServletException.class).hasMessageContaining("message");
    }

    @Test
    public void testDestroy() {
        // Setup

        // Run the test
        corsFilterUnderTest.destroy();

        // Verify the results
    }
}
