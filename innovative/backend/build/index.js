"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const app = (0, express_1.default)();
app.get('/', function (req, res) {
    res.json({ message: "Hello World" });
});
app.get('/products', function (req, res) {
    res.json({ message: "Products" });
});
app.get('/users', function (req, res) {
    res.json({ message: "Users" });
});
app.listen(3000);
