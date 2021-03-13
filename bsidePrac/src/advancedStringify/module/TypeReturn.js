const TypeReturn = class{
    static #instance;
    static init(){
        TypeReturn.#instance = new TypeReturn();
    }
    static get(acc, idx, type, target){
        TypeReturn.#instance.acc = acc;
        TypeReturn.#instance.idx = idx;
        TypeReturn.#instance.type = type;
        TypeReturn.#instance.target = target;
        return TypeReturn.#instance;
    };
};
//왜 init을 하게 해주었을까??
TypeReturn.init();
export {TypeReturn};