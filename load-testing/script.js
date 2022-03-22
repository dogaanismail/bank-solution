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
        accountId: "690ee57d-bcfd-4e09-8ded-7709d4359186",
        amount: 500,
        currency: "EUR",
        direction: "IN",
        description: "Example"
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let res = http.post(transactioUrl, payload, params);
    check(res, {
        "is status 201": (r) => r.status === 200,
        "body contains result accountId": (r) => r.body.includes('SUCCESS')
    });
}