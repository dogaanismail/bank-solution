import http from 'k6/http';
import {check} from "k6";

export const options = {
    stages: [
        {duration: '30s', target: 10},
        {duration: '1m30s', target: 30},
        {duration: '20s', target: 50},
    ],
};

export default function () {
    const transactioUrl = 'http://localhost:5002/api/v1/transaction/createTransaction';

    const payload = JSON.stringify({
        customerId: 1,
        accountId: "47ff5d97-075d-467e-afc2-8d1bfc806130",
        amount: 500,
        currency: "USD",
        direction: "IN",
        description: "LOAD_TESTING_FOR_TRANSACTION"
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let res = http.post(transactioUrl, payload, params);
    check(res, {
        "is status 200": (r) => r.status === 200,
        "body contains result": (r) => r.body.includes('SUCCESS')
    });
}