#include "S32K144.h"
#include "device_registers.h"

void DC_init(void)
{
	 /*
	 * Pins definitions
	 * ===================================================
	 *
	 * Pin number        | Function
	 * ----------------- |------------------
	 * PTD0		     	 | FTM0CH2
	 * PTD1			     | FTM0CH3
	 */
 PCC->PCCn[PCC_PORTD_INDEX ]|=PCC_PCCn_CGC_MASK;   /* Enable clock for PORTD */
 PORTD->PCR[0]|=PORT_PCR_MUX(2);           		/* Port D16: MUX = ALT2, FTM0CH1 */
 PORTD->PCR[1]|=PORT_PCR_MUX(2);           		/* Port D16: MUX = ALT2, FTM0CH3 */
}
