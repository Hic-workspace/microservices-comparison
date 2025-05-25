import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import { Box, Typography } from '@mui/material';

const PerformanceChart = ({ data }) => {
    if (!data || data.length === 0) return null;

    const chartData = [
        {
            name: 'Response Time (ms)',
            OpenFeign: data[1]?.averageResponseTime || 0,
            WebClient: data[0]?.averageResponseTime || 0
        },
        {
            name: 'Requests/Second',
            OpenFeign: Math.round(data[1]?.requestsPerSecond || 0),
            WebClient: Math.round(data[0]?.requestsPerSecond || 0)
        }
    ];

    return (
        <Box sx={{ mt: 4 }}>
            <Typography variant="h6" gutterBottom>
                Performance Comparison
            </Typography>
            <BarChart width={600} height={300} data={chartData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="OpenFeign" fill="#8884d8" />
                <Bar dataKey="WebClient" fill="#82ca9d" />
            </BarChart>
        </Box>
    );
};

export default PerformanceChart;