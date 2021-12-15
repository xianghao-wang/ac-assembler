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
* **Comment**
  * single line comment `//comments`
* **Label**
  * grammar: `(label)`
  * semantics
    * add a lable for conviniently jump
  
* **A instruction**
  * grammar: `@value`
  * semantics
    * load `value` to address register `A`
    * `value` can be a deciaml integer in `[0,16641)`
    * `value` can be a `label` or `variable` 
* **C instruction**
  * grammar: `[destination=]computation[;jump]`
    * `destination`: `A|D|M|AD|AM|DM|ADM`
    * `computation`: see [Supported Computations and Jump Conditions](#supported-computations-and-jump-conditions)
    * `jump`: see [Supported Computations and Jump Conditions](#supported-computations-and-jump-conditions)
  * semantics:
    * load the result of `computation` to `destination`; if the result meets condition `jump`, the address of next instruction will be the one stored in `A`

# Supported Computations and Jump Conditions
## Computations
| A computations | M computations |
| :------------: | :------------: |
|     **0**      |                |
|     **1**      |                |
|     **-1**     |                |
|     **D**      |                |
|     **A**      |     **M**      |
|     **!D**     |                |
|     **!A**     |     **!M**     |
|     **-D**     |                |
|     **-A**     |     **-M**     |
|    **D+1**     |                |
|    **A+1**     |    **M+1**     |
|    **D-1**     |                |
|    **A-1**     |    **M-1**     |
|    **D+A**     |    **D+M**     |
|    **D-A**     |    **D-M**     |
|    **A-D**     |    **M-D**     |
|    **D&A**     |    **D&M**     |
|    **D\|A**    |    **D\|M**    |

## Jump Conditions

|  jump   | description                                        |
| :-----: | :------------------------------------------------- |
| **JMP** | jump without any condition                         |
| **JEQ** | jump when the result is 0                          |
| **JNE** | jump when the result is not 0                      |
| **JGT** | jump when the result is greater than 0             |
| **JLT** | jump when the result is less than 0                |
| **JGE** | jump when the result is greater than or equal to 0 |
| **JLE** | jump when the result is less than or equal to 0    |

