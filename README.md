# ac-assembler
This is an assembler for A-instruction and C-instruction.

This assembler is for course [nand2tetirs](https://www.coursera.org/learn/build-a-computer/home/welcome) programming assignment.

# Manual - Specifications
* **A register** `A`: the register holding an address
* **D register** `D`: the register holding data
* **M register** `M`: the virtual register holding the data in memory located by A register
* **label**: it label a line for conveniently jump, should be in form `[_a-zA-Z]+[0-9]*[_a-zA-Z]*`
* **variable**: the mnemonic representing an address, in form `[_a-zA-Z]+[0-9]*[_a-zA-Z]*`

# Manual - Instructions and Grammars
* Comment
  * single line comment `//`
* Label
  * grammar: `(label)``
  * semantics
    * add a lable for conviniently jump
    
* A instruction
  * grammar: `@value`
  * semantics
    * load `value` to address register `A`
    * `value` can be a deciaml integer in `[0,16641)`
    * `value` can be a `label` or `variable` 
* C instruction
  * grammar: `[destination=]computation[;jump]`
    * `destination`: `A|D|M|AD|AM|DM|ADM`
    * `computation`: see [supported computations and jump conditions]()
    * `jump`: see [supported computations and jump conditions]()
  * semantics:
    * load the result of `computation` to `destination`; if the result meets condition `jump`, the address of next instruction will be the one stored in `A`
