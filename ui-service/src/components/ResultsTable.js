import React from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Typography,
    Box
} from '@mui/material';

const ResultsTable = ({ data }) => {
    if (!data || data.length === 0) return null;

    return (
        <Box sx={{ mt: 4 }}>
            <Typography variant="h6" gutterBottom>
                Detailed Results
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Metric</TableCell>
                            <TableCell align="right">OpenFeign</TableCell>
                            <TableCell align="right">WebClient</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell>Total Duration (ms)</TableCell>
                            <TableCell align="right">{data[1]?.totalDuration}</TableCell>
                            <TableCell align="right">{data[0]?.totalDuration}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Average Response Time (ms)</TableCell>
                            <TableCell align="right">{data[1]?.averageResponseTime}</TableCell>
                            <TableCell align="right">{data[0]?.averageResponseTime}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Requests Per Second</TableCell>
                            <TableCell align="right">
                                {Math.round(data[1]?.requestsPerSecond)}
                            </TableCell>
                            <TableCell align="right">
                                {Math.round(data[0]?.requestsPerSecond)}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Success Count</TableCell>
                            <TableCell align="right">{data[1]?.successCount}</TableCell>
                            <TableCell align="right">{data[0]?.successCount}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Error Count</TableCell>
                            <TableCell align="right">{data[1]?.errorCount}</TableCell>
                            <TableCell align="right">{data[0]?.errorCount}</TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default ResultsTable;