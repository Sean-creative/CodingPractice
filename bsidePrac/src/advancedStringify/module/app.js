import {stringify} from "./stringify.js"

const log =v=>console.log(stringify(v), JSON.stringify(v), stringify(v) === JSON.stringify(v));

const test = {
    a:3,
    b:[1,2, {b:3}],
    c:"abc",
    d:true,
    e:null,
    f:{a:[1,2,3,[4,5,6,[7,{a:3}]]]}
};
log(test);
const test2 =[1,"abc",false, null, undefined];
log(test2);
const test1 = {a:1,b:"abc",c:false, d:null, e:undefined};
log(test1);
const test3 = [1,2,[3,4]];
log(test3);
log(12);
log("12");
log(false);
log(null);
const test4 = [1, Symbol("abc"), function(){}, _=>{}];
log(test4);