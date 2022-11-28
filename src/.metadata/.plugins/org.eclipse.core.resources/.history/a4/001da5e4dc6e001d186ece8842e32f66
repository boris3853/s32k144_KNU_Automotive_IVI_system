#include "device_registers.h"

void GPIO_PORT_INIT()
{
    // GPIO PCC Setting - CGC Masking
    PCC->PCCn[PCC_PORTA_INDEX] |= PCC_PCCn_CGC_MASK;

    /* GPIOA PDDR Setting
    // INPUT: PA6 PA7 PA8 PA9
    // OUTPUT: PA11 PA12 PA13
    */
    PTA->PDDR &= ~((1<<6)|(1<<7)|(1<<8)|(1<<9));
    PTA->PDDR |= (1<<11)|(1<<12)|(1<<13);

    // PORTA: PA6 PA7 PA8 PA9 PA11 PA12 PA13 MUX Setting
    PORTA->PCR[6] |= PORT_PCR_MUX(1);
    PORTA->PCR[7] |= PORT_PCR_MUX(1);
    PORTA->PCR[8] |= PORT_PCR_MUX(1);
    PORTA->PCR[9] |= PORT_PCR_MUX(1);
    PORTA->PCR[11] |= PORT_PCR_MUX(1);
    PORTA->PCR[12] |= PORT_PCR_MUX(1);
    PORTA->PCR[13] |= PORT_PCR_MUX(1);


}

void TEST()
{
	if(!(PTA->PDIR & (1<<6)))
	    PTA->PCOR |= (1<<11);
	else
	    PTA->PSOR |= (1<<11);

	if(!(PTA->PDIR & (1<<7)))
		PTA->PCOR |= (1<<12);
	else
	    PTA->PSOR |= (1<<12);

    if(!(PTA->PDIR & (1<<8)))
        PTA->PCOR |= (1<<13);
    else
        PTA->PSOR |= (1<<13);
}

// test
int main(void){
     GPIO_PORT_INIT();

    while(1)
    {
        TEST();
    }
}
