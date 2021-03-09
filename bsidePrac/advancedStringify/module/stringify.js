import {getCloser} from "./getType.js";
import {accumulate, init} from "./accumulate.js";

const stringify =v=>{
    let {isPrimitive, acc, target, type} = init(v);
    if(!isPrimitive){
        const stack = [];
        let idx = 0;
        do{
            for(;idx < target.length; idx++) ({acc, idx, type, target} = accumulate(target[idx], stack, acc, idx, type, target));
            acc = getCloser(type)(acc);
            if(stack.length === 0) break;
            else ({idx, type, target} = stack.pop());
        }while(true);
    }
    return acc;
};

export {stringify};